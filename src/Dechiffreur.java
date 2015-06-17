import java.util.ArrayList;
import java.util.List;

/** 
 *  Permet de déchiffrer un texte chiffré (par César ou Vigenère)
 *  sans en connaitre la clé.
 * 
 *  @author Antoine Sachet
 *  Created in Oct 2013
 */
public class Dechiffreur {
	
	public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	/*
	 * Longueur à partir de laquelle on arrête de chercher la clé
	 * (évite une boucle infinie si on a dépassé la conne longueur)
	 */
	public static int LONGUEUR_CLE_MAX = 100;
   
	/* 
	 * Valeur des indices de coicidence pour un texte anglais, français, chiffré (répartition uniforme)
	 */
	public static double IC_ANGLAIS = 1.73;
	public static double IC_FRANCAIS = 2.02;
	public static double IC_UNIFORME = 1.0;
	
	public String trouverCle(String texte) throws Exception {
	   String cle = "";
		
		int lgCle = trouverLongueurCle(texte);
		List<String> sousTextes = parser(texte, lgCle);
		for (String s : sousTextes){
			cle += trouverCleCesar(s);
		}
	
		return cle;
		
   }
   
   /** Calcule la longueur de clé la plus probable
    *  jusqu'à une longueur maximale de 20 caractères
    * @param texteChiffre
    * @return longueur probable de la clé utilisée pour chiffrer l'entrée
 * @throws Exception si la longueur de la clé n'a pas été trouvé.
    */
   public int trouverLongueurCle(String texteChiffre) throws Exception{
	   int lgCle = 1;
	   Boolean found = false;
	   while(!found && lgCle<LONGUEUR_CLE_MAX) {
		
		   // création des sous-textes en prenant une lettre tout les lgCle caractères
		   List<String> sousTexte = parser(texteChiffre, lgCle);
		   
		   // calcul de la moyenne de l'indice de coincidence pour cette lg de clé
		   double moyIC = 0; 
		   for (String s : sousTexte){
			   moyIC += getIndiceDeCoincidence(s);
		   }
		   moyIC = moyIC / lgCle;
		   
		   //System.out.println("Pour la lg de clé "+lgCle+ ", on calcule un IC moyen de : " +moyIC);
		   if (Math.abs(moyIC-IC_UNIFORME) > 0.5)
			   return lgCle;
		   
		   lgCle++;
	   }
	   
	   throw new Exception ("Impossible de trouver la longueur de la clé (recherche des clés < "+LONGUEUR_CLE_MAX+").");
   }
   
   /**
    * Découpe le texte d'entrée en n sous-textes 
    * (un caractère pris tous les n caractères)
    * @param texte
    * @param n
    * @return liste des sous-textes
    */
   private List<String> parser(String texte, int n){
	   
	   List<StringBuilder> l = new ArrayList<StringBuilder>();
	   for (int i=0; i<n; i++){
		   l.add(new StringBuilder());
	   }
	   
	   int lgTexte=texte.length();
	   for (int i=0; i<lgTexte; i++){
		   l.get(i%n).append(texte.charAt(i));
	   }
	   
	   List<String> subText = new ArrayList<String>();
	   for (int i=0; i<n; i++){
		   subText.add(l.get(i).toString());
	   }
	   return subText;
   }
   
   private String dechiffrer(String texte, String cle) {
	 return new ChiffreVigenere(cle).dechiffrer(texte);
   }
   
   /** 
    * Déchiffre un texte chiffré par la méthode de Vigenère
    * @param texte
    * @return texte déchiffré
    */
   public String dechiffrer(String texte) {
	   String cle = "";
	   try {
		   cle = trouverCle(texte);
	} catch (Exception e) {
		System.out.println("Impossible de déchiffrer le texte");
		e.printStackTrace();
	}
	   return dechiffrer(texte, cle);
   }
   
   /** 
    * Calcule l'indice de coincidence du texte donné en entrée
    * @param texte
    * @return indice de coincidence du texte
    */
   public double getIndiceDeCoincidence(String texte) {
	   double indiceCoincidence = 0;
	   int n = texte.length();
	   for (char c : alphabet){
		   double nC = 0;
		   for(int i=0; i<n; i++) {
			   if (texte.charAt(i)==c)
				   nC++;
		   }
		   indiceCoincidence += (nC/n)*((nC-1)/(n-1));
	   }
	   indiceCoincidence *= 26;
	   return indiceCoincidence;
   }
   
   /**
    * Calcule la clé chiffrant le texte d'entrée
    * qu'on suppose chiffré par un chiffre de César
    * 
    * On détermine la lettre la plus fréquente dans le texte ;
    * En français le 'e' est de loin la lettre la plus fréquente
    * --> donne la clé
    * 
    * @param texte chiffré par un chiffre de César
    * @return clé du chiffre
    */
   public char trouverCleCesar(String texte){
	   double apparitionMax = 0;
	   char lettreLaPlusFrequente = 'a';
	   int n = texte.length();
	   for (char c : alphabet){
		   double apparition = 0;
		   for(int i=0; i<n; i++) {
			   if (texte.charAt(i)==c)
				   apparition++;
		   }
		   if (apparition > apparitionMax){
			   apparitionMax = apparition;
			   lettreLaPlusFrequente = c;
		   }
	   }
	   
	   // on part du principe que la lettre la plus fréquente code le 'e'
	   int decalage = ChiffreCesar.getIndex(lettreLaPlusFrequente)-ChiffreCesar.getIndex('e'); //décalage entre -4 et 21
	   decalage = (decalage + alphabet.length ) % (alphabet.length); //ramène le décalage entre 0 et 25
	   char cle = ChiffreCesar.getLettre(decalage);
	   
	   return cle;
   }
   
   }

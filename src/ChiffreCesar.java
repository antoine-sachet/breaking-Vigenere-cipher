/**
 * Permet le chiffrement d'un texte par le chiffre de César
 * à partir d'une clé de type char.
 * 
 * @author Antoine Sachet
 * Created in Oct 2013
 */
public class ChiffreCesar implements Chiffre {
	
	/** cle est un INDEX ENTRE O ET alphabet.length()-1 = 25+5 */
	private int cle;
	static public String alphabet = "abcdefghijklmnopqrstuvwxyz";
   
   /** Renvoie l'INDEX dans l'alphabet (entre 0 et 25) du caractère c.
    * @param caractère
    * @return l'index dans l'alphabet du caractère 
    */
   public static int getIndex(char c) {
		  return alphabet.indexOf(c);
   }
   
   /** Pour passer de l'index à la lettre
    * @param l'index dans l'alphabet du caractère
    * @return  le caractère
    */
   public static char getLettre(int index) {
		  return alphabet.charAt(index);
   }
   
   
   /** Chiffre 'texte' avec la clé retenue
    * @param texte : texte à chiffrer
    * @return texte chiffré
    */
   public String chiffrer(String texte) {
	   String texteChiffre = "" ;
	   for (int i=0 ; i<texte.length(); i++) {
		   texteChiffre += chiffrer(texte.charAt(i));
	   }
	   return texteChiffre;
   }
   
   /**
    * Renvoie le caractère en entrée chiffrer avec la clé retenue
    */
   public char chiffrer(char c) {
		   int indexChiffre = (getIndex(c) + this.cle) % alphabet.length();
		   return getLettre(indexChiffre);
   }
   
   /** Dechiffre un texte chiffré avec la clé retenue
    * @param texte chiffré
    * @return texte dechiffré
    */
   public String dechiffrer(String texte) {
	   return new ChiffreCesar(getLettre((alphabet.length() - cle)%alphabet.length())).chiffrer(texte);
   }
  
   public char dechiffrer(char c) {
	   int indexDechiffre = (getIndex(c)  + alphabet.length() - this.cle) % alphabet.length();
	   return getLettre(indexDechiffre);
   }
   
   public ChiffreCesar (char c) {
	   this.cle = alphabet.indexOf(c);
   }
   
   }

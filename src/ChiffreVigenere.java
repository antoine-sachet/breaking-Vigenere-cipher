import java.util.List;
import java.util.ArrayList;

/**
 * Permet le chiffrement d'un texte par le chiffre de Vigenère
 * à partir d'une clé de type String.
 * 
 * @author Antoine Sachet
 * Created in Oct 2013
 */
public class ChiffreVigenere implements Chiffre {
   private String cle;
   
   /**
    * Un chiffre de Vigenère est un cycle de chiffre de César
    */
   private List<ChiffreCesar> chiffreCesar;
      
   /** 
    * Chiffre le texte selon la méthode de Vigenère avec la clé retenue.
    * ON SUPPOSE LE TEXTE NE CONTENANT QUE DES LETTRES (cf Programme.assainir())
    * @param texte
    * @return texte chiffré
    */
   public String chiffrer(String texte) {
      String texteChiffre = "";
      int lgCle = chiffreCesar.size();
	   for (int i=0; i<texte.length(); i++){
    	  char c = texte.charAt(i);
    	  char cChiffre = chiffreCesar.get(i%lgCle).chiffrer(c);
    	  texteChiffre += cChiffre;
      }
	   return texteChiffre;
   }
   
   /**
    * Déchiffre le texte avec la clé retenue
    * 
    */
   public String dechiffrer(String texte) {
	   String texteDechiffre = "";
	      int lgCle = chiffreCesar.size();
	      for (int i=0; i<texte.length(); i++){
	    	  char c = texte.charAt(i);
	    	  char cDechiffre = chiffreCesar.get(i%lgCle).dechiffrer(c);
	    	  texteDechiffre += cDechiffre;
	      }
		  return texteDechiffre;
   }
   
   /**
    * Crée un nouveau chiffreVigenère initialisé avec la clé donnée
    * @param cle
    *
    */
   public ChiffreVigenere (String cle) {
	   this.cle = cle;
	   this.chiffreCesar = new ArrayList<ChiffreCesar>();
	   for (char c : this.cle.toCharArray()){
    	  chiffreCesar.add(new ChiffreCesar(c));
      }
   }
   
   }

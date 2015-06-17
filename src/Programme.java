import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;

/**
 * Programme principal r�alisant une d�monstration
 * des diff�rentes m�thodes de chiffrement impl�ment�es
 * sur un extrait de Jules Vernes et un extrait des Mis�rables
 * 
 * @author Antoine Sachet
 * Created in Oct 2013
 */
public class Programme {

	public static void main(String[] args) {
		System.out.println("Exemple d'attaque du chiffre de Vigen�re : ");
		
		/* Choix de la cl� et du texte en clair */
		String cle = "caribou";
		String texteClair = "";
		try {
			texteClair = readFile("data/Vernes.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		System.out.println("Cl� choisie : " + cle);
		System.out.println("Texte choisi (150 premiers caract�res) : \n" + texteClair.substring(0, 150) );
		ChiffreVigenere vig = new ChiffreVigenere(cle);
		
		
		System.out.println("Chiffrement :");
		String texteAssaini = assainir(texteClair);
		System.out.println("Texte assaini : " + texteAssaini.substring(0, 150) );
		
		String texteChiffre = vig.chiffrer(texteAssaini);
		System.out.println("Texte chiffr� : " + texteChiffre.substring(0, 150) );
		
		System.out.println("Dechiffrement : ");
		
		
		Dechiffreur d = new Dechiffreur();
		try {
			System.out.println("Longueur de la cl� : "+ d.trouverLongueurCle(texteChiffre));
			System.out.println("Cl� : " + d.trouverCle(texteChiffre));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String texteDechiffre = d.dechiffrer(texteChiffre);
		System.out.println("Texte dechiffr� : " + texteDechiffre.substring(0, 150));
	}
	
	public void test(Chiffre chiffre, String texteClair){
		String texteAssaini = assainir(texteClair);
		String texteChiffre = chiffre.chiffrer(texteAssaini);
		String texteDechiffre = chiffre.dechiffrer(texteChiffre);
		
		System.out.println("Test du chiffre \n");
		System.out.println("texte d'origine : " + texteClair);
		System.out.println("texte 'assaini' : " + texteAssaini);
		System.out.println("texte chiffr� : " + texteChiffre);
		System.out.println("texte d�chiffr� : " + texteDechiffre);
	}
	
	public void testV(){
		Chiffre vigenere = new ChiffreVigenere("ceco"); //47 caract�res
		String s1 = "";
		try {
			s1 = readFile("data/les_mis�rables_1_chapter.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(s1);
		String s2 = assainir(s1);
		System.out.println(s2);
		String s3 = vigenere.chiffrer(s2);
		System.out.println(s3);
		
		Dechiffreur d = new Dechiffreur();
		
		System.out.println(d.getIndiceDeCoincidence(s1));
		
		try {
			System.out.println(d.trouverLongueurCle(s3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(vigenere.dechiffrer(s3));
		
	}
	
	/** 
	 * �te de l'entr�e tous les symboles autres que les lettres
	 * et convertit tout en minuscule.
	 * @param texte : texte � assainir
	 * @return texte assaini
	 */
	static public String assainir(String texte) {
		// transforme les accents en 2 caract�res (ex : "�" devient "e^")
		String texteAssaini = Normalizer.normalize(texte, Normalizer.Form.NFD);
		// ne garde que les caract�res de a � z et de A � Z
		String texteAssaini2 = texteAssaini.replaceAll("[^a-zA-Z]", "");
		// convertit le texte en minuscule
		String texteAssaini3 = texteAssaini2.toLowerCase();
		return texteAssaini3;
	}
/**
 * Charge un fichier dans une string
 * @param pathname
 * @return string : contenu du fichier
 * @throws IOException
 */
	private static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
}

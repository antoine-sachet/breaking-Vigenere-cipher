import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;

/**
 * Programme principal réalisant une démonstration
 * des différentes méthodes de chiffrement implémentées
 * sur un extrait de Jules Vernes et un extrait des Misérables
 * 
 * @author Antoine Sachet
 * Created in Oct 2013
 */
public class Programme {

	public static void main(String[] args) {
		System.out.println("Exemple d'attaque du chiffre de Vigenère : ");
		
		/* Choix de la clé et du texte en clair */
		String cle = "caribou";
		String texteClair = "";
		try {
			texteClair = readFile("data/Vernes.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		System.out.println("Clé choisie : " + cle);
		System.out.println("Texte choisi (150 premiers caractères) : \n" + texteClair.substring(0, 150) );
		ChiffreVigenere vig = new ChiffreVigenere(cle);
		
		
		System.out.println("Chiffrement :");
		String texteAssaini = assainir(texteClair);
		System.out.println("Texte assaini : " + texteAssaini.substring(0, 150) );
		
		String texteChiffre = vig.chiffrer(texteAssaini);
		System.out.println("Texte chiffré : " + texteChiffre.substring(0, 150) );
		
		System.out.println("Dechiffrement : ");
		
		
		Dechiffreur d = new Dechiffreur();
		try {
			System.out.println("Longueur de la clé : "+ d.trouverLongueurCle(texteChiffre));
			System.out.println("Clé : " + d.trouverCle(texteChiffre));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String texteDechiffre = d.dechiffrer(texteChiffre);
		System.out.println("Texte dechiffré : " + texteDechiffre.substring(0, 150));
	}
	
	public void test(Chiffre chiffre, String texteClair){
		String texteAssaini = assainir(texteClair);
		String texteChiffre = chiffre.chiffrer(texteAssaini);
		String texteDechiffre = chiffre.dechiffrer(texteChiffre);
		
		System.out.println("Test du chiffre \n");
		System.out.println("texte d'origine : " + texteClair);
		System.out.println("texte 'assaini' : " + texteAssaini);
		System.out.println("texte chiffré : " + texteChiffre);
		System.out.println("texte déchiffré : " + texteDechiffre);
	}
	
	public void testV(){
		Chiffre vigenere = new ChiffreVigenere("ceco"); //47 caractères
		String s1 = "";
		try {
			s1 = readFile("data/les_misérables_1_chapter.txt");
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
	 * ôte de l'entrée tous les symboles autres que les lettres
	 * et convertit tout en minuscule.
	 * @param texte : texte à assainir
	 * @return texte assaini
	 */
	static public String assainir(String texte) {
		// transforme les accents en 2 caractères (ex : "ê" devient "e^")
		String texteAssaini = Normalizer.normalize(texte, Normalizer.Form.NFD);
		// ne garde que les caractères de a à z et de A à Z
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

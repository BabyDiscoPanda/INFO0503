// /**
//  * Objet pour tester la classe JeuDeMot
//  * 
//  * @author J.-C. BOISSON (2023)
//  * @version 1.0
//  */
// public class TestJeuDeMots {

//     /**
//      * Constructeur (inutile) pour éviter un warning lors de la génération de la doc */
//     private TestJeuDeMots(){}

//     /**
//      * Méthode proposée juste pour vérifier le bon fonctionnement de l'objet
//      * 
//      * @param args Les paramètres de la ligne de commande (seul le premier est utilisé s'il existe)
//      */
//     public static void main(String[] args) {

// 	char separateur         = java.io.File.separatorChar;
// 	String fichierParDefaut = "misc"+separateur+"ListeDeMots_01.json";

// 	JeuDeMots jeu=null;
	
// 	// C'est juste pour montrer le genre de truc bizarre qu'on peut faire
//         jeu = JeuDeMots.fromJSON(
// 				 switch(args.length){
// 				 case 1  -> args[0];
// 				 default -> fichierParDefaut;
// 				 }
// 				 );
	
//         // C'est simple et propre ici
// 	/*if(args.length == 1) 
// 	    jeu=JeuDeMots.fromJSON(args[0]);
// 	else
// 	jeu=JeuDeMots.fromJSON(fichierParDefaut);*/

// 	try {
// 	    System.out.println(jeu);
// 	    var scanner = new java.util.Scanner(System.in);
// 	    System.out.println("\nTest de vérification");
// 	    System.out.print("Donnez un 1er intrus : ");
// 	    String intrus1 = scanner.nextLine();
// 	    System.out.print("Donnez un 2nd intrus : ");
// 	    String intrus2 = scanner.nextLine();

// 	    System.out.println("Les intrus sont-ils \""+intrus1+"\" et \""+intrus2+"\" ? "+(jeu.validation(intrus1,intrus2)?"OUI":"NON"));

// 	    System.out.println();
	    
// 	    scanner.close();
// 	} catch(Exception e){
//             System.err.println(e);
//         } 
//     }
// }

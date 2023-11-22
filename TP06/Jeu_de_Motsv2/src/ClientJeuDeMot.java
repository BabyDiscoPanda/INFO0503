import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;

/**
 * ClientJeuDeMot
 */
public class ClientJeuDeMot {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String choix1;
        String choix2;
        String on = "on";
        String donnees,listeDonnees = null;
        JeuDeMots JDM = JeuDeMots.fromJSON("misc/ListeDeMots_02.json");

        System.out.println("BIENVENUE DANS JEU DE MOT !\n\n VOICI LES MOTS :\n"+JDM+"\nLES QUELLES SONT LES INTRUS ?");
        System.out.println("\nPREMIER CHOIX :");
        choix1 = sc.nextLine();
        System.out.println("\nDEUXIEME CHOIX :");
        choix2 = sc.nextLine();

        try {
            listeDonnees = URLEncoder.encode(choix1, "UTF-8") + "=" + URLEncoder.encode(on, "UTF-8") + "&" + URLEncoder.encode(choix2, "UTF-8") + "=" + URLEncoder.encode(on, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de l'encodage : " + e);
            System.exit(0);  
        }
        
        sc.close();

        URL url = null;
        try { 
            url = new URL("http://localhost:8080/verification"); 
        } catch(MalformedURLException e) { 
            System.err.println("URL incorrect : " + e);
            System.exit(0);
        }
        
        // Etablissement de la connexion
        URLConnection connexion = null; 
        try { 
            connexion = url.openConnection(); 
            connexion.setDoOutput(true);
        } catch(IOException e) { 
            System.err.println("Connexion impossible : " + e);
            System.exit(0);
        } 
        
        // Envoi de la requête
        try {
            OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
            writer.write(listeDonnees);
            writer.flush();
            writer.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de la requete : " + e);
            System.exit(0);            
        }
        System.out.println("\n\n\n");
        // Réception des données depuis le serveur
        donnees = ""; 
        try { 
            BufferedReader reader = new BufferedReader(new InputStreamReader( connexion.getInputStream())); 
            String tmp; 
            while((tmp = reader.readLine()) != null) 
                donnees += tmp; 
            reader.close(); 
        } catch(Exception e) { 
            System.err.println("Erreur lors de la lecture de la réponse : " + e);
            System.exit(0);
        }
        
        // Affichage des données reçues
        System.out.println("Réponse du serveur : ");
        System.out.println(donnees);
    }
    
}
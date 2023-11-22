import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;



public class ClientChiffrement {
    
    public static final int portEcoute = 5001;
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        
        if(args.length != 1) {
            System.err.println("Utilisation :");
            System.err.println("  java Chiffrement clePublique message output");
            System.err.println("    où :");
            System.err.println("      - clePublique : nom du fichier qui contient la clé publique");
            System.exit(0);        
        }

        String pays;
        Type TypeBois;
        int tonne,cO2;

        System.out.println("Pays : ");
        pays = sc.nextLine();
        System.out.println("1 : Chene\n2 : Sapin\n3 : Bouleau");
        tonne = sc.nextInt();
        switch (tonne) {
            case 1:
                TypeBois = Type.Chen;
                break;
            case 2:
                TypeBois = Type.Sapi;
                break;
            case 3:
                TypeBois = Type.Boul;
                break;
            default:
                TypeBois = Type.Chen;
                break;
        }
        System.out.println("Poids :");
        tonne = sc.nextInt();
        System.out.println("Consomation :");
        cO2 = sc.nextInt();
        Bois mon_bois = new Bois(TypeBois,tonne,pays,cO2);

        // Recuperation de la cle publique
        PublicKey clePublique = GestionClesRSA.lectureClePublique(args[0]);

        // Chiffrement du message
        byte[] bytes = null;
        try {
            Cipher chiffreur = Cipher.getInstance("RSA");
            chiffreur.init(Cipher.ENCRYPT_MODE, clePublique);
            bytes = chiffreur.doFinal(mon_bois.toString().getBytes());
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        } catch(NoSuchPaddingException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        } catch(InvalidKeyException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        } catch(IllegalBlockSizeException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        } catch(BadPaddingException e) {
            System.err.println("Erreur lors du chiffrement : " + e);
            System.exit(0);
        } 

        // Création de la socket
        Socket socket = null;
        try {
            socket = new Socket("localhost", portEcoute);
        } catch(UnknownHostException e) {
            System.err.println("Erreur sur l'hôte : " + e);
            System.exit(0);
        } catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }
    
        // Association d'un flux d'entrée et de sortie
        OutputStream output = null; 
        DataOutputStream input = null;

        try {
            output = socket.getOutputStream();
            input = new DataOutputStream(output);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        // Envoi de 'Bonjour'
        System.out.println("Envoi: " + mon_bois);
        int len = bytes.length;

        try{
            input.writeInt(len);
            input.write(bytes,0,len);
        }catch(Exception e){
            System.exit(0);
        }
        

        // Fermeture des flux et de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
            System.exit(0);
        }
    }

}

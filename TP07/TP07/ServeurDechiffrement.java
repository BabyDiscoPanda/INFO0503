import java.security.PrivateKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServeurDechiffrement {
    public static final int portEcoute = 5001;

    public static void main(String[] args) {

        if(args.length != 1) {
            System.err.println("Utilisation :");
            System.err.println("  java ServeurDechiffrement clePrivee");
            System.err.println("    où :");
            System.err.println("      - clePrivee : nom du fichier qui contient la clé privée");
            System.exit(0);        
        }

        // Création de la socket serveur
        ServerSocket socketServeur = null;
        try {    
            socketServeur = new ServerSocket(portEcoute);
        } catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        // Attente d'une connexion d'un client
        Socket socketClient = null;
        try {
            socketClient = socketServeur.accept();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }

        // Association d'un flux d'entrée et de sortie
        InputStream input = null;
        DataInputStream output = null;
        try {
            input = socketClient.getInputStream();
            output = new DataInputStream(input);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        // Lecture
        int len = 0;
        try {
            len = output.readInt();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }

        // Récupération de la clé privée
        PrivateKey clePrivee = GestionClesRSA.lectureClePrivee(args[0]);

        // Chargement du message chiffré
        byte[] data = new byte[len];

        try {
            output.readFully(data);
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        

        // Déchiffrement du message
        byte[] bytes = new byte[len];
        try {
            Cipher dechiffreur = Cipher.getInstance("RSA");
            dechiffreur.init(Cipher.DECRYPT_MODE, clePrivee);
            bytes = dechiffreur.doFinal(data);
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

        // Affichage du message
        String messageDecode = new String(bytes);
        System.out.println("Message : " + messageDecode);
        Bois testBois = new Bois(messageDecode);
        System.out.println("Objet construit a partir d'un string : " + testBois);
        // Fermeture des flux et des sockets
        try {
            input.close();
            output.close();
            socketClient.close();
            socketServeur.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
            System.exit(0);
        }
    }
}

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe correspondant à un serveur UDP.
 * La chaine de caractères "Bonjour" est envoyée au serveur.
 * Le port d'écoute du serveur est indiqué dans la classe ServeurUDP.
 * @author Cyril Rabat
 */
public class ServeurUDP {

    public static int portEcoute = 2025;
    public static int portRetour = 2026;

    public static void main(String[] args) {
        // Création de la socket
        DatagramSocket socket = null;
        DatagramSocket socket2 = null;
        try {        
            socket = new DatagramSocket(portEcoute);
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création du message
        byte[] tampon = new byte[1024];
        DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

        // Lecture du message du client
        try {
            socket.receive(msg);
            String texte = new String(msg.getData(), 0, msg.getLength());
            System.out.println("Lu: " + texte);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();

        //retour
        //ouverture socket
        try {
            socket2 = new DatagramSocket();
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }
        // crétaion message
        DatagramPacket msg2 = null;
        try {
            InetAddress adresse2 = InetAddress.getByName(null);
            String message2 = "Bonjour, nickel et toi ?";
            byte[] tampon2 = message2.getBytes();
            msg2 = new DatagramPacket(tampon2, tampon2.length, adresse2, portRetour);
            
        } catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }
        //envoie du message
        try {
            socket2.send(msg2);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
        //fermeture socket
        socket2.close();
    }

}
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe correspondant à un client UDP.
 * La chaine de caractères "Bonjour" est envoyée au serveur.
 * Le port d'écoute du serveur est indiqué dans la classe ServeurUDP.
 * @author Cyril Rabat
 */
public class ClientUDP {

    public static int portEcoute = 2025;
    public static int portRetour = 2026;
    
    public static void main(String[] args) {
        DatagramSocket socket = null;
        DatagramSocket socket2 = null;
        // Création de la socket
        try {
            socket = new DatagramSocket();
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création du message
        DatagramPacket msg = null;
        try {
            InetAddress adresse = InetAddress.getByName(null);
            String message = "Bonjour ca va ?";
            byte[] tampon = message.getBytes();
            msg = new DatagramPacket(tampon, tampon.length, adresse, portEcoute);
            
        } catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }

        // Envoi du message
        try {
            socket.send(msg);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();

        // Retour
        try{
            socket2 = new DatagramSocket(portRetour);
        }catch(SocketException e){
            System.err.println("Erreur de création sur la socket2 : "+e);
            System.exit(0);
        }
        byte[] tampon2 = new byte[2048];
        DatagramPacket msg2 = new DatagramPacket(tampon2,tampon2.length);
    
        try {
            socket2.receive(msg2);
            String texte = new String(msg2.getData(),0,msg2.getLength());
            System.out.println("Recu: "+ texte);
        } catch (IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }
        //Fermeture
        socket2.close();
    
    }

}
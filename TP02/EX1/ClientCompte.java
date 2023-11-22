import java.io.IOException;

import java.util.Scanner;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientCompte {
    public static int portEcouteClientServeur = 2025;
    public static int portEcouteServeurClient = 2026;
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DatagramSocket socketClientServeur = null;
        DatagramSocket socketServeurClient = null;
        boolean stop = true;
        String messageClientServeur =null;
        String messageServeurClient =null;
        int choix;

        //créa socket
        try {
            socketClientServeur = new DatagramSocket();
            socketServeurClient = new DatagramSocket(portEcouteServeurClient);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        //créa tampon

        byte[] tamponClientServeur = null;
        byte[] tamponServeurClient = new byte[1024];

        DatagramPacket msgClientServeur = null;
        DatagramPacket msgServeurClient = new DatagramPacket(tamponServeurClient,tamponServeurClient.length);
        InetAddress adresse = null;
        //créa innetadresse
        try{
            adresse = InetAddress.getByName(null);
        }catch(Exception e){
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }

        while(stop){
            //envoie du message au serveur
            System.out.println("Entrez votre nombre");
            messageClientServeur = sc.nextLine();
            tamponClientServeur = messageClientServeur.getBytes();
            msgClientServeur = new DatagramPacket(tamponClientServeur, tamponClientServeur.length, adresse, portEcouteClientServeur);
            try {
                socketClientServeur.send(msgClientServeur);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }
            //reception du message
            try {
                socketServeurClient.receive(msgServeurClient);
                messageServeurClient = new String(msgServeurClient.getData(),0,msgServeurClient.getLength());
            } catch (Exception e) {
                System.err.println("Erreur reception du message : " + e);
                System.exit(0);
            }

            //traitement des données

            if(messageServeurClient==null){
                System.out.println("problème de reception de donnée");
            }else{
                if(messageServeurClient.equals("STOP")){
                    stop = false;
                }else{
                    if(!messageServeurClient.equals("OUI"))
                        System.out.println(messageServeurClient);
                }
            }
        }

        socketClientServeur.close();
        socketServeurClient.close();
        
    }
}

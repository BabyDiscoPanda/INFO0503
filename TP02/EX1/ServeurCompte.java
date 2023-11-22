import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServeurCompte {
    public static int portEcouteClientServeur = 2025;
    public static int portEcouteServeurClient = 2026;
    public static void main(String[] args) {
        DatagramSocket socketClientServeur = null;
        DatagramSocket socketServeurClient = null;
        boolean stop = true;
        String messageClientServeur =null;
        String messageServeurClient =null;
        int depart = -1;
        int tmp = 0;
        //création des sockets
        
        try {
            socketClientServeur = new DatagramSocket(portEcouteClientServeur);
            socketServeurClient = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }   

        // création des tampons
        byte[] tamponClientServeur = new byte[1024];
        byte[] tamponServeurClient = null;

        DatagramPacket msgClientServeur = new DatagramPacket(tamponClientServeur,tamponClientServeur.length);
        DatagramPacket msgServeurClient = null;

        //création de l'inetadress
        InetAddress addresseServeurClient = null;
        try {
            addresseServeurClient = InetAddress.getByName(null);
        } catch (Exception e) {
            System.exit(0);
        }

        //Lecture et envoie de message
        while(stop){
            try {
                socketClientServeur.receive(msgClientServeur);
                messageClientServeur = new String(msgClientServeur.getData(),0,msgClientServeur.getLength());
                if(messageClientServeur.equals("STOP")){
                    stop = false;
                    messageServeurClient = "STOP";
                    tamponServeurClient = messageServeurClient.getBytes();
                    msgServeurClient = new DatagramPacket(tamponServeurClient, tamponServeurClient.length,addresseServeurClient,portEcouteServeurClient);
                    socketServeurClient.send(msgServeurClient);
                }else{
                    if (depart==-1) {
                        try{
                            depart = Integer.parseInt(messageClientServeur);
                            System.out.println("Valeur de départ initialisé : "+depart);
                            messageServeurClient= "OUI";
                        }catch(Exception e){
                            depart = -1;
                            messageServeurClient = "Resaisir valeur départ !";
                        }
                    } else {
                        try{
                            tmp = Integer.parseInt(messageClientServeur);
                        }catch(Exception e){
                            depart = 0;
                            System.out.println("oups repart a 0");
                            messageServeurClient = "Repart à 0 !";
                        }
                        if(tmp == depart +1){
                            depart+=1;
                            System.out.println("Oui, nouvelle valeur attendu : "+(depart+1));
                            messageServeurClient = "OUI";
                        }else{
                            System.out.println("repart a 0");
                            messageServeurClient = "Repart à 0 !";
                            depart = 0;
                        }
                    }
                    tamponServeurClient = messageServeurClient.getBytes();
                    msgServeurClient = new DatagramPacket(tamponServeurClient, tamponServeurClient.length,addresseServeurClient,portEcouteServeurClient);
                    socketServeurClient.send(msgServeurClient);
                }
            } catch (Exception e) {
                System.out.println("ERREUR : "+e);
                System.exit(0);
            }
        }

        socketClientServeur.close();
        socketServeurClient.close();

    }
}

package com.supermeuble;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Planete {
    
    public static int portEcoute = 3031;
    public static ArrayList<Bois> stockBois = new ArrayList<Bois>();

    public static void main(String[] args) {
        int nbClient = 0;
        // Création de la socket
        DatagramSocket socket = null;
        int portArrive = -1;
        boolean close = true;
        try {        
            socket = new DatagramSocket(portEcoute);
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }

        // Lecture du message du client
        DatagramPacket msgRecu = null;
        byte[] tampon = new byte[1024];
        msgRecu = new DatagramPacket(tampon, tampon.length);



        while(close){
            try {
                socket.receive(msgRecu);
            } catch(IOException e) {
                System.err.println("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }
        
            // Récupération de l'objet
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Requete r = (Requete) ois.readObject();
                
                System.out.println("Recu : " + r);
            
                switch(r.getTypeMessage().toInt()){
                    case 1:
                        //connexion au serveur
                        nbClient++;
                        System.out.println("nb client : " + nbClient);
                        break;
                    case 2 :
                        //depot de bois
                        stockBois.add(r.getRequeteBois());
                        portArrive = r.getPort();
                        r.setTypeMessage(TypeMSG.Created);
                        envoieServeur(r, socket, portArrive);
                        break;
                    case 3 :
                        //envoie de bois
                        int i = stockBois.indexOf(r.getRequeteBois());
                        r.setRequeteBois(stockBois.get(i));
                        stockBois.remove(i);
                        portArrive = r.getPort();
                        break;
                    case 4 :
                        //déco du serveur
                        nbClient--;
                        System.out.println("nb client : " + nbClient);
                        if(nbClient<=0)
                            close = false;
                        break;
                    default:
                        break;
                }
            
            } catch(ClassNotFoundException e) {
                System.err.println("Objet reçu non reconnu : " + e);
                System.exit(0);
            } catch(IOException e) {
                System.err.println("Erreur lors de la récupération de l'objet : " + e);
                System.exit(0);
            }

            //envoie de la réponse

        }
        
            
        socket.close();
    }

    public static void envoieServeur(Requete r,DatagramSocket socket,int port){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(r);
        } catch(IOException e) {
            System.err.println("Erreur lors de la sérialisation : " + e);
            System.exit(0);
        }
        //envoie
        try {
            byte[] donnees = baos.toByteArray();
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length, 
                                                    adresse, port);
            socket.send(msg);
        } catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création de l'adresse : " + e);
            System.exit(0); 
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

    }

}

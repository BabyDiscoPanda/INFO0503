package com.supermeuble;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Fennec {
    public static int portEcoute = 3031;

    public static void main(String[] args) {

        // Création de la socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }
        //connexion au serveur
        Requete r = new Requete(socket.getLocalPort());
        r.setTypeMessage(TypeMSG.CONNEXION);
        envoieServeur(r, socket);
        
        // Création du bois
        Bois b = new Bois(Type.Boul,8, "GERMANY", 90);
        r.setRequeteBois(b);
        r.setTypeMessage(TypeMSG.ENVBOIS);

        //envoie du bois
        envoieServeur(r, socket);

        //mon bois a-t-il était crée ?
        DatagramPacket msgRecu = null;
        byte[] tampon = new byte[1024];
        msgRecu = new DatagramPacket(tampon, tampon.length);

        try {
            socket.receive(msgRecu);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            r = (Requete) ois.readObject();
            System.out.println("Recu : " + r);
        }
        catch(ClassNotFoundException e) {
            System.err.println("Objet reçu non reconnu : " + e);
            System.exit(0);
        }
        catch(IOException e) {
            System.err.println("Erreur lors de la récupération de l'objet : " + e);
            System.exit(0);
        }

        //deconnexion
        r.setTypeMessage(TypeMSG.SAYONARA);
        envoieServeur(r, socket);

        socket.close();
    }

    public static void envoieServeur(Requete r,DatagramSocket socket){
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
                                                    adresse, portEcoute);
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

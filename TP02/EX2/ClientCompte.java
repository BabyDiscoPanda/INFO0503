import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ClientCompte {
    public static int portEcoute = 3031;
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Client Moi = null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] donnees=null;
        InetAddress adresse = null;
        DatagramPacket msg;
        String nombre=null;

        //création du socket
        try {
            socket = new DatagramSocket();
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }

        //création de l'objet client :
        Moi = new Client(socket.getLocalPort());

        //création en ByteArray

        //connexion et envoie au serveur en UDP
        while(nombre == null || ){
            try {
                System.out.println("choisir un nombre :");
                nombre = sc.nextLine();
                Moi.setNombre(nombre);
                System.out.println("je vais envoyé: ["+Moi+"]");
                baos = new ByteArrayOutputStream();
                try {
                    oos = new ObjectOutputStream(baos);
                    oos.writeObject(Moi);
                } catch(IOException e) {
                    System.err.println("Erreur lors de la sérialisation : " + e);
                    System.exit(0);
                }
                donnees = baos.toByteArray();
                adresse = InetAddress.getByName("localhost");
                msg = new DatagramPacket(donnees, donnees.length, 
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
        

        socket.close();

    }
}

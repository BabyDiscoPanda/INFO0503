import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class Planete {

    public static int portEcoute = 3031;
    public static ArrayList<Bois> stockageBois = new ArrayList<Bois>();

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
            
                switch(r.getTypeMessage()){
                    case 0 :
                        nbClient++;
                        //connexion au serveur
                        break;
                    case 1 :
                        //depot de bois
                        stockageBois.add(r.getRequeteBois());
                        portArrive = r.getPort();
                        break;
                    case 2 :
                        int i = stockageBois.indexOf(r.getRequeteBois());
                        r.setRequeteBois(stockageBois.get(i));
                        stockageBois.remove(i);
                        portArrive = r.getPort();
                        break;
                    case 3 :
                        //déco du serveur
                        nbClient--;
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
        }
        
            
        socket.close();
    }

    

}
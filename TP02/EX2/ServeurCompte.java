import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ServeurCompte {
    public static int portEcoute = 3031;
    public static void main(String[] args) throws SocketException {
        int precPort;
        DatagramSocket socket = null;
        DatagramPacket msgRecu = null;
        byte[] tampon = new byte[1024];
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Client ClientTmp = null;
        boolean boucle = true;

        //création de la socket avec un timeout de 5s
        try {        
            socket = new DatagramSocket(portEcoute);
            System.out.println("Je suis connecté ! et mon timeout est de "+socket.getSoTimeout()+" ms");
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création du socket : " + e);
            System.exit(0);
        }


        tampon = new byte[1024];
                msgRecu = new DatagramPacket(tampon, tampon.length);

        while(boucle){
            try {
                socket.receive(msgRecu);
                System.out.println("message reçu !");
            } catch(IOException e) {
                System.err.println("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }
            try {
                bais = new ByteArrayInputStream(msgRecu.getData());
                ois = new ObjectInputStream(bais);
                ClientTmp = (Client) ois.readObject();
                precPort = ClientTmp.getPort();
                System.out.println("Recu : " + ClientTmp);
                System.out.println("port mis a jour : " + precPort);
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

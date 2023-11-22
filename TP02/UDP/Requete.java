import java.io.Serializable;

public class Requete implements Serializable {
    private Bois RequeteBois;
    private int port;
    private int typeMessage;

    public Requete(int port) {
        this.port = port;
    }

    public Bois getRequeteBois() {
        return RequeteBois;
    }

    public void setRequeteBois(Bois requeteBois) {
        this.RequeteBois = requeteBois;
    }

    public int getPort() {
        return port;
    }

    public int getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(int typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        return "Requete [RequeteBois=" + RequeteBois + ", port=" + port + ", typeMessage=" + typeMessage + "]";
    }

    
}

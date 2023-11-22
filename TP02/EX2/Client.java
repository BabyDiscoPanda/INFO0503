import java.io.Serializable;

public class Client implements Serializable {
    private int port;
    private String nombre;

    public Client(int port){
        this.port = port;
        nombre = "0";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString(){
        return "mon port : "+this.port+" le nombre choisi : "+this.nombre;
    }

}

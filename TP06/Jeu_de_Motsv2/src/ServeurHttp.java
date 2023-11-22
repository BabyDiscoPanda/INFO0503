import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

public class ServeurHttp {
    public static char separateur         = java.io.File.separatorChar;
    public static String fichierParDefaut = "misc"+separateur+"ListeDeMots_02.json";

    public static JeuDeMots JDM = JeuDeMots.fromJSON(fichierParDefaut);
    public static void main(String[] args) {    
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/verification", new VerificationHandler(JDM));
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");
    }
}
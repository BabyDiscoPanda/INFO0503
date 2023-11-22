import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;

public class VerificationHandler implements HttpHandler {
    
    private JeuDeMots JDM;

    public VerificationHandler(JeuDeMots jDM) {
        JDM = jDM;
    }


    public void setJDM(JeuDeMots JDM){
        this.JDM = JDM;
    }

    public void handle(HttpExchange t) {
        String reponse = "<!DOCTYPE html>" +
        "<html lang=\"fr\">" +
        "<head>" +
        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>" +
        "</head>" +
        "<body>" +
        "<h1>Bienvenue sur la page de verification</h1>";
        
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        String  [] tabQuerry = null;
        String [] repQuery = new String[2];
        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }

        // Affichage des données
        reponse += "<p>Données en POST : ";
        if(query == null)
            reponse += "<b>Aucune</b></p>";
        else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
            } catch(UnsupportedEncodingException e) {
                query = "";
            }
            tabQuerry = new String[query.split("&").length];
            tabQuerry = query.split("&");
            repQuery[0] = tabQuerry[0].split("=")[0];
            repQuery[1] = tabQuerry[1].split("=")[0];
            if(JDM.validation(repQuery[0], repQuery[1]))
                reponse += "<b> GG ! </b></p>";
            else
                reponse += "<b> Perdu ! </b></p>";
        }


        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }
}
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * Objet qui correspond à une liste de mots dont 2 sont des intrus
 * 
 * @author J.-C. BOISSON (2021-2023)
 * @version 1.2
 */
public class JeuDeMots {

    /** La liste des mots du jeu (incluant les intrus) */
    private ArrayList<String> mots;

    /** Les 2 intrus de la liste de mots */
    private ArrayList<String> intrus;

    /**
     * Constructeur d'un objet JeuDeMots
     * 
     * @param mots La liste des mots (contenant les intrus)
     * @param intrus Les intrus spécifiques (qui doivent être dans la liste originelle)
     */
    public JeuDeMots(ArrayList<String> mots, ArrayList<String> intrus) {

	this.mots   = mots;
        this.intrus = intrus;

        if( intrus.size() != 2)
            throw new RuntimeException("Il doit y avoir 2 intrus (pas plus, pas moins)");

        if( intrus.get(0).equals(intrus.get(1)) )
            throw new RuntimeException("Les intrus doivent être deux mots différents");

        if(! mots.contains(intrus.get(0)) || ! mots.contains(intrus.get(1)) )
            throw new RuntimeException("Au moins un des intrus n'est pas dans la liste");
    }

    /**
     * Surcharge du toString classique de Object
     * 
     * @return Le détail de la configuration de l'instance courante JeuDeMots
     */
    @Override
    public String toString() {

	// Pour stocker tout ce qui va être écrit
	var baos = new java.io.ByteArrayOutputStream();

	// Pour avoir accès au printf
	var ps = new java.io.PrintStream(baos);

	// Utilisation des stream pour récupérer la taille du plus grand String
	int max_length =  mots.stream().max(java.util.Comparator.comparing(String::length)).get().length();

	// On l'incrémente pour éviter que le plus grand String soit collé à un autre
	max_length++;
	
	ps.print("Mots   : [ ");

        var it = mots.iterator();
        while(it.hasNext())
	    // La notation %-Xs permet de conserver la casse en assurant un alignement à gauche sur X caractères
	    ps.printf("%-"+max_length+"s",it.next());
	ps.println("]");

	ps.print("Intrus : [ ");
        it = intrus.iterator();
        while(it.hasNext())
	    ps.printf("%-"+max_length+"s",it.next());

	ps.print("]");
	ps.close();

	return baos.toString();
    }

    /**
     * Méthode transformant en JSONObject l'instance courante de JeuDeMots
     * 
     * @return Le JSONObject correspondant
     */
    public JSONObject toJSON() {
	
        var json = new JSONObject();
        
        json.put("mots"  , mots);
        json.put("intrus", intrus);
        
        return json;
    }

    /**
     * Generation d'un objet JeuDeMots depuis un fichier json
     * @param path Chemin vers un fichier json
     * @return L'objet JeuDeMots créé
     */
    public static JeuDeMots fromJSON(String path) {
	
        String json = null;

        var mots   = new ArrayList<String>();
        var intrus = new ArrayList<String>();

        try { 
            json = new String(java.nio.file.Files.readAllBytes(new java.io.File(path).toPath())); }
        catch(java.io.IOException ioe) {
            System.out.println(ioe);
        }
        finally {
                     
            var jsonObject = new JSONObject(json);

	    // Devinez-vous le type de "array" ?
            var array = jsonObject.getJSONArray("mots");

	    // Devinez-vous le type de "it" ?
            var it = array.iterator();
            while(it.hasNext())
                mots.add(it.next().toString());

            array = jsonObject.getJSONArray("intrus");

            it = array.iterator();
            while(it.hasNext()) 
                intrus.add(it.next().toString());
        }
        
        return new JeuDeMots(mots,intrus);
    }

    /**
     * Fonction qui indique si la proposition est bonne
     * 
     * @param mot1 Le premier intrus
     * @param mot2 Le second intrus
     * 
     * @return vrai (true) ou faux (false) suivant si la proposition est bonne ou mauvaise.
     */
    public boolean validation(String mot1, String mot2) {
        return intrus.contains(mot1) && intrus.contains(mot2) && !mot1.equals(mot2);
    }

    /**
     * Getter pour la liste de mots
     * 
     * @return La liste des mots
     */
    public ArrayList<String> getMots() { 
        return this.mots;
    }
}

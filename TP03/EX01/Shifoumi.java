import java.io.Serializable;
import java.util.Random;

public class Shifoumi implements Serializable{
    private Main.Level Mouvement;

    public Shifoumi(Main.Level Mouvement){
        this.Mouvement=Mouvement;
    }
    public Shifoumi(){
        Random rand = new Random();
        int doigt = rand.nextInt(3);
        switch(doigt){
            case 0 :
                this.Mouvement = Main.Level.Papyrus;
                break;
            case 1 :
                this.Mouvement = Main.Level.Gravillon;
                break;
            case 2 :
                this.Mouvement = Main.Level.Couteau;
                break;
        }
    }
    public Resultat compare(Main.Level contreMvt){
        Resultat res = null;
        switch(this.Mouvement){
            case Papyrus:
                switch(contreMvt){
                    case Papyrus :
                        res = Resultat.Nul;
                        break;
                    case Gravillon:
                        res = Resultat.Gagne;
                        break;
                    case Couteau:
                        res = Resultat.Perd;
                        break;
                }
                break;
            case Gravillon:
                switch(contreMvt){
                    case Papyrus :
                        res = Resultat.Perd;
                        break;
                    case Gravillon:
                        res = Resultat.Nul;
                        break;
                    case Couteau:
                        res = Resultat.Gagne;
                        break;
                }
                break;
            case Couteau:
                switch(contreMvt){
                    case Papyrus :
                        res = Resultat.Gagne;
                        break;
                    case Gravillon:
                        res = Resultat.Perd;
                        break;
                    case Couteau:
                        res = Resultat.Nul;
                        break;
                }
                break;
        }
        return res;
    }
}

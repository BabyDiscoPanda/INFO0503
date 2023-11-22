import java.io.Serializable;

public class Bois implements Serializable {

    public static final int version = 1;
    private Type TypeBois;
    private int Tonne;
    private String Pays;
    private int CO2;


    public Bois(Type typeBois, int tonne, String pays, int cO2) {
        TypeBois = typeBois;
        Tonne = tonne;
        if(pays.length()>3){
            Pays =""+pays.charAt(0)+""+ pays.charAt(1) +""+ pays.charAt(2);
        }else{
            Pays = pays;
        }
        
        CO2 = cO2;
    }


    public Bois(Type typeBois, int tonne, int cO2) {
        TypeBois = typeBois;
        Tonne = tonne;
        CO2 = cO2;
        Pays = null;
    }


    public Type getTypeBois() {
        return TypeBois;
    }


    public void setTypeBois(Type typeBois) {
        TypeBois = typeBois;
    }


    public int getTonne() {
        return Tonne;
    }


    public void setTonne(int tonne) {
        Tonne = tonne;
    }


    public String getPays() {
        return Pays;
    }


    public void setPays(String pays) {
        Pays = pays;
    }


    public int getCO2() {
        return CO2;
    }    

    @Override
    public boolean equals(Object b2) {
        Bois b3 = (Bois)b2;
        if(this.CO2<=b3.CO2 && this.Pays.equals(b3.Pays)&& b3.Tonne<= this.Tonne && this.TypeBois == b3.TypeBois){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        return Pays+TypeBois.toString().toUpperCase()+"T"+Tonne+"T"+"C"+CO2+"C";
    }
    

}

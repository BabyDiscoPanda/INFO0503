package com.supermeuble;

public class TestRequeteBois {
    public static void main(String[] args) {
        Bois monBois = new Bois(Type.Boul, 90, "Allemagne", 90);
        Requete MaRequete = new Requete(500);
        MaRequete.setTypeMessage(TypeMSG.CONNEXION);
        System.out.println(monBois);
        System.out.println(MaRequete);
    }
}
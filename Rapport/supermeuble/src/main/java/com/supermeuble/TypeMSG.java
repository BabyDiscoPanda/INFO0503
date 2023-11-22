package com.supermeuble;

public enum TypeMSG{
    CONNEXION(1),RECUPBOIS(3),ENVBOIS(2),SAYONARA(4),Created(201),Refuse(403);

    private final int val;

    TypeMSG(int val){
        this.val = val;
    }

    public int toInt(){
        return val;
    }

}

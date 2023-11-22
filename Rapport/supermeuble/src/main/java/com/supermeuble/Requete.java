package com.supermeuble;

import java.io.Serializable;

public class Requete implements Serializable {
    private Bois RequeteBois;
    private int port;
    private int num;
    private TypeMSG typeMessage;

    public Requete(int port) {
        this.port = port;
        this.RequeteBois = null;
        this.typeMessage = null;
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

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public TypeMSG getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMSG typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        String tmp = "Requete [RequeteBois=";
        if (RequeteBois == null){
            tmp+= "null";
        }else{
            tmp+= RequeteBois;
        }

        
        tmp += ", port=" + port;

        if (typeMessage==null){
            tmp += ",TypeMessage=null]";
        }else{
            tmp += ",TypeMessage="+typeMessage+"]";
        }

        return tmp;
    }

    
}

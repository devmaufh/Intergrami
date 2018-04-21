package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Tierras {
    int id_tierra;
    String temporal, tamaño, urlfoto;
    public Tierras(int id,String tempo, String tamaño,String url){
        this.id_tierra=id;
        this.temporal=tempo;
        this.tamaño=tamaño;
        this.urlfoto=url;

    }

    public int getId_tierra() {
        return id_tierra;
    }

    public String getTemporal() {
        return temporal;
    }

    public String getTamaño() {
        return tamaño;
    }

    public String getUrlfoto() {
        return urlfoto;
    }


}

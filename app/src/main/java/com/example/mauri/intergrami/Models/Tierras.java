package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Tierras {
    String id_tierra;
    String tamaño, monto, urlfoto;
    public Tierras(){
    }
    public String getId_tierra() {
        return id_tierra;
    }

    public void setId_tierra(String id_tierra) {
        this.id_tierra = id_tierra;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }
}

package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Mis_productos {
    String id_compra;
    String monto;
    String nombre,descripcion,urlfoto;
    public Mis_productos(String id_compra, String monto, String nombre, String descripcion,String urlfoto){
        this.id_compra=id_compra;
        this.monto=monto;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.urlfoto=urlfoto;
    }

    public String getId_compra() {
        return id_compra;
    }

    public String getMonto() {
        return monto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlfoto() {
        return urlfoto;
    }
}

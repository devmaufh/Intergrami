package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Mis_productos {
    int id_compra;
    double monto;
    String nombre,descripcion,urlfoto;
    public Mis_productos(int id_compra, double monto, String nombre, String descripcion,String urlfoto){
        this.id_compra=id_compra;
        this.monto=monto;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.urlfoto=urlfoto;
    }

    public int getId_compra() {
        return id_compra;
    }

    public double getMonto() {
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

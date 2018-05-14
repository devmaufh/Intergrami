package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Mis_productos {
    String id_compra;
    String monto;
    String nombre,descripcion,urlfoto;
    public Mis_productos(){
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
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

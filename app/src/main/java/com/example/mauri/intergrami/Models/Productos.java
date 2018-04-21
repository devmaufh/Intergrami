package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Productos {
    int id_product;
    String nombre;
    double precio;
    String urlFoto;
    public Productos(int id,String name,double price, String url){
        this.id_product=id;
        this.nombre=name;
        this.precio=price;
        this.urlFoto=url;
    }

    public int getId_product() {
        return id_product;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getUrlFoto() {
        return urlFoto;
    }
}

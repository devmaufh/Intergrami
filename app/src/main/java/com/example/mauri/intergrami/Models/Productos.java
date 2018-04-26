package com.example.mauri.intergrami.Models;

/**
 * Created by mauri on 21/04/2018.
 */

public class Productos {
    String id_product;
    String nombre;
    String precio;
    String urlFoto;
    String descripcion;
    String fecha;
    public Productos(){
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_product() {
        return id_product;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}

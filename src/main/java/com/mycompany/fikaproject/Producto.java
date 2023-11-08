package com.mycompany.fikaproject;

import lombok.Data;

@Data
public class Producto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private String imagenUrl;
    
    public Producto() {
    }

    public Producto(String nombre, String categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }
    public Producto(int id, String nombre, String categoria, double precio, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenURL) {
        this.imagenUrl = imagenURL;
    }

  
}

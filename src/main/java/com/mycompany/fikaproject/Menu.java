package com.mycompany.fikaproject;

import java.util.List;

public class Menu {
    private int id;
    private String nombre;
    private String descripcion;
    private List<Producto> productos;
    private double descuento;
    private double aumento;
    private double precioTotal;

    public Menu() {
    }

    public Menu(String nombre, String descripcion, List<Producto> productos, double descuento, double aumento, double precioTotal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = productos;
        this.descuento = descuento;
        this.aumento = aumento;
        this.precioTotal = precioTotal;
    }

    Menu(int id, String nombre, double precioTotal) {
        this.nombre = nombre;
        this.precioTotal = precioTotal;
    }

    // Getters y setters

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getAumento() {
        return aumento;
    }

    public void setAumento(double aumento) {
        this.aumento = aumento;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}


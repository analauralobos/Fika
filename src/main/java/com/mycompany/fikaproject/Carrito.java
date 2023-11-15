package com.mycompany.fikaproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrito {
    int carritoId;
    int clienteId;
    Date fechaCreacion;
    List<Producto> productos;
    List<Menu> menus;
    double precioTotal;
    
    public Carrito() {
        this.productos = new ArrayList<>();
        this.menus = new ArrayList<>();
    }

    Carrito(int carritoId, int clienteId, Date fechaCreacion, List<Producto> productos, List<Menu> menus, double precioTotal) {
        this.carritoId = carritoId;
        this.clienteId = clienteId;
        this.fechaCreacion = fechaCreacion;
        this.productos = productos;
        this.menus = menus;
        this.precioTotal = precioTotal;
    }

    public int getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(int carritoId) {
        this.carritoId = carritoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }


    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
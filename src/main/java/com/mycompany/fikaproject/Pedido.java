package com.mycompany.fikaproject;
import java.util.Date;
import lombok.Data;

@Data
public class Pedido {
    private int id;
    private Usuario cliente;
    private Menu menus;
    private Date fechaPedido;
    private Date fechaEntrega;
    private String estado;

    public Pedido(int id, Menu menus, Usuario cliente, Date fechaPedido, Date fechaEntrega, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.menus = menus;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }
    


    // Getters y setters (métodos para acceder y modificar los atributos)

    public int getId() {
        return id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Menu getMenus() {
        return menus;
    }

    public void setMenus(Menu menus) {
        this.menus = menus;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

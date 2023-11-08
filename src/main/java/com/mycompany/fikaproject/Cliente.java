package com.mycompany.fikaproject;

import lombok.Data;

@Data
public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private int edad;

    public Cliente(int id, String nombre, String email, String telefono, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.edad = edad;
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", edad=" + edad +
                '}';
    }

    int getId() {
        return id; //To change body of generated methods, choose Tools | Templates.
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }
    
}

package com.mycompany.fikaproject;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String telefono;
    private int edad;
    private String rol;

    public Usuario(int id, String nombre, String email,String contraseña, String telefono, int edad, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.edad = edad;
        this.rol = rol;
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
        return id; 
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }
    
}

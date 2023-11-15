package com.mycompany.fikaproject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void agregarCliente(String nombre, String email, String telefono, int edad, String contraseña) {
    String query = "INSERT INTO usuarios (id, nombre, email, telefono, edad, contraseña, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, 0);
        statement.setString(2, nombre);
        statement.setString(3, email);
        statement.setString(4, telefono);
        statement.setInt(5, edad);
        statement.setString(6, contraseña);
        statement.setString(7, "CLIENTE");

        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> clientes = new ArrayList<>();

        try {
            String query = "SELECT id, nombre, email, telefono, edad ,contraseña, rol FROM usuarios";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");                
                int edad = resultSet.getInt("edad");
                String contraseña = resultSet.getString("contraseña");
                String rol = resultSet.getString("rol");
                
                Usuario cliente = new Usuario(id, nombre, email,contraseña, telefono, edad, rol);
                clientes.add(cliente);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

public Usuario obtenerUsuarioPorId(int id) {
    try {
        String query = "SELECT id, nombre, email, telefono, edad ,contraseña, rol FROM usuarios WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int clienteId = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String email = resultSet.getString("email");
            String telefono = resultSet.getString("telefono");
            int edad = resultSet.getInt("edad");
            String contraseña = resultSet.getString("contraseña");
            String rol = resultSet.getString("rol");

            Usuario cliente = new Usuario(clienteId, nombre, email, contraseña, telefono, edad, rol);
            resultSet.close();
            statement.close();
            return cliente;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Retorna null si no se encuentra el usuario con el ID especificado
}

public Usuario obtenerUsuarioPorCredenciales(String email, String contraseña) {
    connection = DatabaseConnection.getConnection();
    PreparedStatement statement = null; 
    try {
        String query = "SELECT id, nombre, email, telefono, edad, contraseña, rol FROM usuarios WHERE email = ? AND contraseña = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, email);  
        statement.setString(2, contraseña); 
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int clienteId = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String telefono = resultSet.getString("telefono");
            int edad = resultSet.getInt("edad");
            String rol = resultSet.getString("rol");
            Usuario cliente = new Usuario(clienteId, nombre, email, contraseña, telefono, edad, rol);
            return cliente;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return null;
}


}

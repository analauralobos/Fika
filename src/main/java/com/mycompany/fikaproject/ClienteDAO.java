package com.mycompany.fikaproject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Connection connection;

    public ClienteDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void agregarCliente(Cliente cliente) {
        // Implementa la lógica para insertar un nuevo cliente en la base de datos
    }

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try {
            String query = "SELECT id, nombre, email, telefono, edad FROM clientes";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");
                int edad = resultSet.getInt("edad");

                Cliente cliente = new Cliente(id, nombre, email, telefono, edad);
                clientes.add(cliente);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

public Cliente obtenerClientePorId(int id) {
    try {
        String query = "SELECT id, nombre, email, telefono, edad FROM clientes WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int clienteId = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String email = resultSet.getString("email");
            String telefono = resultSet.getString("telefono");
            int edad = resultSet.getInt("edad");

            Cliente cliente = new Cliente(clienteId, nombre, email, telefono, edad);
            resultSet.close();
            statement.close();
            return cliente;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Retorna null si no se encuentra el cliente con el ID especificado
}

}



/*
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClienteDAO {
    private List<Cliente> clientes;
    private Random random;

    public ClienteDAO() {
        // Inicializa una lista de clientes en memoria
        this.clientes = new ArrayList<>();
        this.random = new Random();

        // Agregar algunos clientes de prueba aleatorios
        agregarClientesDePrueba();
    }

    // Método para agregar un nuevo cliente
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clientes;
    }

    // Método para obtener un cliente por su ID
    public Cliente obtenerClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null; // Retorna null si el cliente no se encuentra
    }

    // Método para agregar clientes de prueba aleatorios
    private void agregarClientesDePrueba() {
        for (int i = 1; i <= 10; i++) {
            String nombre = "Cliente" + i;
            String email = "cliente" + i + "@ejemplo.com";
            String telefono = "+123456789" + i;
            int edad = random.nextInt(50) + 20; // Edad aleatoria entre 20 y 69 años

            // Crea un nuevo cliente aleatorio y lo agrega a la lista
            Cliente cliente = new Cliente(i, nombre, email, telefono, edad);
            agregarCliente(cliente);
        }
    }
}
*/
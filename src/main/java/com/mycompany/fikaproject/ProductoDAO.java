package com.mycompany.fikaproject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final Connection connection;

    public ProductoDAO() {
        this.connection = DatabaseConnection.getConnection();
    }


    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();

        try {
            String query = "SELECT id, nombre, categoria, precio ,imagenURL FROM productos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String categoria = resultSet.getString("categoria");
                double precio = resultSet.getDouble("precio");
                String imagenUrl = resultSet.getString("imagenURL");
                Producto producto = new Producto( id, nombre, categoria, precio, imagenUrl);
                productos.add(producto);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    public Producto obtenerProductoPorId(int idProducto) {
    try {
        String query = "SELECT id, nombre, categoria, precio FROM productos WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProducto);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String categoria = resultSet.getString("categoria");
            double precio = resultSet.getDouble("precio");

            System.out.println("Producto encontrado: ID=" + id + ", Nombre=" + nombre + ", Categoría=" + categoria + ", Precio=" + precio);

            return new Producto(nombre, categoria, precio);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Retorna null si el producto no se encuentra
}
    

}

package com.mycompany.fikaproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MenuDAO {

    private Connection connection;

    public MenuDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void agregarMenu(Menu menu, List<String> cantidadesP) {
  
    try {
        connection.setAutoCommit(false); // Desactiva la confirmación automática para realizar una transacción.

        // 1. Inserta el menú en la tabla 'menus'.
        String menuQuery = "INSERT INTO menus (nombre, descripcion, precio_total, descuento, aumento) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement menuStatement = connection.prepareStatement(menuQuery, Statement.RETURN_GENERATED_KEYS);
        menuStatement.setString(1, menu.getNombre());
        menuStatement.setString(2, menu.getDescripcion());
        menuStatement.setDouble(3, menu.getPrecioTotal());
        menuStatement.setDouble(4, menu.getDescuento());
        menuStatement.setDouble(5, menu.getAumento());
        menuStatement.executeUpdate();

        // Obtiene el ID del menú recién insertado.
        ResultSet generatedKeys = menuStatement.getGeneratedKeys();
        int menuId = -1;
        if (generatedKeys.next()) {
            menuId = generatedKeys.getInt(1);
        } else {
            throw new SQLException("No se pudo obtener el ID del menú recién insertado.");
        }
        menuStatement.close();
        
        
        // 2. Inserta los productos relacionados en la tabla 'menu_producto' con sus cantidades.
        String menuProductosQuery = "INSERT INTO menu_producto (menu_id, producto_id, cantidad) VALUES (?, ?, ?)";
        PreparedStatement menuProductosStatement = connection.prepareStatement(menuProductosQuery);
        for (int i = 0; i < menu.getProductos().size(); i++) {
            
            Producto producto = menu.getProductos().get(i);
            System.out.println("menuDAO prod id" + producto.getId());
            menuProductosStatement.setInt(1, menuId);
            menuProductosStatement.setInt(2, producto.getId());
            int cantidad = Integer.parseInt(cantidadesP.get(i)); // Obtén la cantidad correspondiente
            menuProductosStatement.setInt(3, cantidad);
            menuProductosStatement.executeUpdate();
        }
        menuProductosStatement.close();

        connection.commit(); // Confirma la transacción si todo fue exitoso.
    } catch (SQLException e) {
        try {
            connection.rollback(); // En caso de error, realiza un rollback para deshacer los cambios.
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace(); // Manejo de errores al hacer un rollback.
        }
        e.printStackTrace();
    } finally {
        try {
            connection.setAutoCommit(true); // Reactiva la confirmación automática.
            connection.close();
        } catch (SQLException closeException) {
            closeException.printStackTrace(); // Manejo de errores al cerrar la conexión.
        }
    }
}


    public List<Menu> obtenerTodosLosMenus() {
        List<Menu> menus = new ArrayList<>();
        connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT m.id, m.nombre, m.descripcion, m.descuento, m.aumento, m.precio_total, p.id AS producto_id, p.nombre AS producto_nombre, p.categoria, p.precio "
                    + "FROM menus AS m "
                    + "LEFT JOIN menu_producto AS mp ON m.id = mp.menu_id "
                    + "LEFT JOIN productos AS p ON mp.producto_id = p.id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Menu> menuMap = new HashMap<>(); // Usaremos un mapa para evitar duplicados de menús

            while (resultSet.next()) {
                int menuId = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                double precioTotal = resultSet.getDouble("precio_total");
                double descuento = resultSet.getDouble("descuento");
                double aumento = resultSet.getDouble("aumento");

                // Verificar si ya hemos creado este menú
                Menu menu = menuMap.get(menuId);

                if (menu == null) {
                    menu = new Menu(nombre, descripcion, new ArrayList<>(), descuento, aumento, precioTotal);
                    menu.setId(menuId);
                    menuMap.put(menuId, menu);
                    menus.add(menu);
                }

                int productoId = resultSet.getInt("producto_id");
                String productoNombre = resultSet.getString("producto_nombre");
                String categoria = resultSet.getString("categoria");
                double productoPrecio = resultSet.getDouble("precio");

                Producto producto = new Producto(productoNombre, categoria, productoPrecio);
                menu.getProductos().add(producto); // Agregar el producto al menú correspondiente
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menus;
    }

    public Menu obtenerMenuPorId(int id) {
        try {
            String query = "SELECT m.id, m.nombre, m.descripcion, m.precio_total, m.descuento, m.aumento, p.id AS producto_id, p.nombre AS producto_nombre, p.categoria, p.precio "
                    + "FROM menus AS m "
                    + "LEFT JOIN menu_producto AS mp ON m.id = mp.menu_id "
                    + "LEFT JOIN productos AS p ON mp.producto_id = p.id "
                    + "WHERE m.id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Menu menu = null;

            while (resultSet.next()) {
                if (menu == null) {
                    int menuId = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    double precioTotal = resultSet.getDouble("precio_total");
                    double descuento = resultSet.getDouble("descuento");
                    double aumento = resultSet.getDouble("aumento");

                    menu = new Menu(nombre, descripcion, new ArrayList<>(), descuento, aumento, precioTotal);
                    menu.setId(menuId);
                }

                int productoId = resultSet.getInt("producto_id");
                String productoNombre = resultSet.getString("producto_nombre");
                String categoria = resultSet.getString("categoria");
                double productoPrecio = resultSet.getDouble("precio");

                Producto producto = new Producto(productoNombre, categoria, productoPrecio);
                menu.getProductos().add(producto);
            }

            resultSet.close();
            statement.close();

            return menu;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public Producto obtenerProductoPorId(int idProducto) {
        try {
            String query = "SELECT id, nombre, categoria, precio, imagenURL FROM productos WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProducto);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String categoria = resultSet.getString("categoria");
                double precio = resultSet.getDouble("precio");
                String imagenUrl = resultSet.getString("imagenURL");
                return new Producto(id, nombre, categoria, precio, imagenUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna null si el producto no se encuentra
    }

    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();

        try {
            String query = "SELECT id, nombre, categoria, precio, imagenURL FROM productos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String categoria = resultSet.getString("categoria");
                double precio = resultSet.getDouble("precio");
                String imagenUrl = resultSet.getString("imagenURL");
                
                Producto producto = new Producto(id, nombre, categoria, precio, imagenUrl);
                productos.add(producto);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    public List<Producto> obtenerProductosPorMenu(int idMenu) {
        List<Producto> productos = new ArrayList<>();

        try {
            String query = "SELECT p.id, p.nombre, p.categoria, p.precio "
                    + "FROM productos p "
                    + "INNER JOIN menu_producto mp ON p.id = mp.producto_id "
                    + "WHERE mp.menu_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idMenu);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String categoria = resultSet.getString("categoria");
                double precio = resultSet.getDouble("precio");

                Producto producto = new Producto(nombre, categoria, precio);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public double calcularPrecioTotalMenu(List<Producto> productosSeleccionados, double descuento, double aumento, List<String> cantidadesP) {
        double precioTotal = 0.0;
        System.out.println("MenuDAO: calcTot: desc:" + descuento);
        if (productosSeleccionados.size() != cantidadesP.size()) {
            throw new IllegalArgumentException("La lista de productos y cantidades no tienen la misma longitud.");
        }

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto producto = productosSeleccionados.get(i);
            int cantidad = Integer.parseInt(cantidadesP.get(i));

            if (cantidad > 0) {
                precioTotal += producto.getPrecio() * cantidad;
            }
        }

        // Aplica el descuento y el aumento
        precioTotal = precioTotal - (precioTotal * (descuento / 100)) + (precioTotal * (aumento / 100));

        return precioTotal;
    }

}

package com.mycompany.fikaproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarritoDAO {

    private final Connection connection;

    public CarritoDAO(Connection connection) {
        this.connection = connection;
    }

    public CarritoDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void agregarProductoAlCarrito(int clienteId, int productoId) {

        Carrito carrito = obtenerCarritoPorId(clienteId);
        Producto producto = obtenerProductoPorId(productoId);

        if (carrito == null) {
            crearCarrito(clienteId, producto);
        }

        String queryInsert = "INSERT INTO carrito_productos (carrito_id, producto_id) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
            insertStatement.setInt(1, carrito.getCarritoId());
            insertStatement.setInt(2, productoId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Actualizar el precio total con el nuevo producto.
        double aux = producto.getPrecio();
        double total = carrito.getPrecioTotal() + aux;

        String queryUpdate = "UPDATE carritos SET total = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
            updateStatement.setDouble(1, total);
            updateStatement.setInt(2, carrito.getCarritoId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void crearCarrito(int clienteId, Menu menu) {
        System.out.println("CarritoDAO: crearCarrito() ");
        String query = "INSERT INTO carritos (id, cliente_id, fecha_creacion, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 0);
            statement.setInt(2, clienteId);
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
            statement.setDouble(4, menu.getPrecioTotal());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    public void crearCarrito(int clienteId, Producto producto) {
        System.out.println("CarritoDAO: crearCarrito() ");

        String query = "INSERT INTO carritos (id, cliente_id, fecha_creacion, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 0);
            statement.setInt(2, clienteId);
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
            statement.setDouble(4, producto.getPrecio());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void agregarMenuAlCarrito(int clienteId, int menuId) {

        Carrito carrito = obtenerCarritoPorId(clienteId);
        Menu menu = obtenerMenuPorId(menuId);

        if (carrito == null) {
            crearCarrito(clienteId, menu);
        }

        String query = "INSERT INTO carrito_productos (carrito_id, menu_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carrito.getCarritoId());
            statement.setInt(2, menuId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Actualizar el precio total con el nuevo menu.
        double aux = menu.getPrecioTotal();
        double total = carrito.getPrecioTotal() + aux;

        String queryUpdate = "UPDATE carritos SET total = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
            updateStatement.setDouble(1, total);
            updateStatement.setInt(2, carrito.getCarritoId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Carrito obtenerCarritoPorId(int sesionClienteId) {
        String query = "SELECT id, cliente_id, fecha_creacion FROM carritos WHERE cliente_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sesionClienteId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int carritoId = resultSet.getInt("id");
                    int clienteId = resultSet.getInt("cliente_id");
                    Date fechaCreacion = resultSet.getDate("fecha_creacion");

                    // Obtener los productos y menús asociados al carrito
                    List<Producto> productos = obtenerProductosEnCarrito(carritoId);
                    List<Menu> menus = obtenerMenusEnCarrito(carritoId);

                    // Calcular el precio total sumando los precios de productos y menús
                    double precioTotal = calcularPrecioTotal(productos, menus);

                    Carrito carrito = new Carrito(carritoId, clienteId, fechaCreacion, productos, menus, precioTotal);
                    return carrito;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // En caso de error o si no se encuentra el carrito, devuelve null.
    }

    private List<Producto> obtenerProductosEnCarrito(int carritoId) {
        List<Producto> productos = new ArrayList<>();

        String query = "SELECT p.id, p.nombre, p.precio , p.categoria , p.imagenUrl FROM productos p "
                + "JOIN carrito_productos cp ON p.id = cp.producto_id "
                + "WHERE cp.carrito_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carritoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String categoria = resultSet.getString("categoria");
                    double precio = resultSet.getDouble("precio");
                    String imagenURL = resultSet.getString("imagenUrl");

                    Producto producto = new Producto(id, nombre, categoria, precio, imagenURL);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    private List<Menu> obtenerMenusEnCarrito(int carritoId) {
        List<Menu> menus = new ArrayList<>();

        String query = "SELECT m.id, m.nombre, m.precio_total FROM menus m "
                + "JOIN carrito_productos cm ON m.id = cm.menu_id "
                + "WHERE cm.carrito_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carritoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    double precioTotal = resultSet.getDouble("precio_total");

                    Menu menu = new Menu(id, nombre, precioTotal);
                    menus.add(menu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menus;
    }

    private double calcularPrecioTotal(List<Producto> productos, List<Menu> menus) {
        double total = 0;

        // Sumar los precios de los productos
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }

        // Sumar los precios de los menús
        for (Menu menu : menus) {
            total += menu.getPrecioTotal();
        }

        return total;
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

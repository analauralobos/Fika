package com.mycompany.fikaproject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoDAO {
    private final Connection connection;
    private final ClienteDAO clienteDAO; // Agregamos una referencia a la clase ClienteDAO
    private final MenuDAO menuDAO; // Agregamos una referencia a la clase MenuDAO
    public PedidoDAO(Connection connection, ClienteDAO clienteDAO, MenuDAO menuDAO) {
        this.connection = connection;
        this.clienteDAO = clienteDAO;
        this.menuDAO = menuDAO;
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            String query = "SELECT id, fecha_pedido, fecha_entrega, estado, cliente_id , menu_id FROM pedidos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fechaPedidoStr = resultSet.getString("fecha_pedido");
                String fechaEntregaStr = resultSet.getString("fecha_entrega");
                String estado = resultSet.getString("estado");
                int clienteId = resultSet.getInt("cliente_id");
                int menuId = resultSet.getInt("menu_id");
                // Convierte las cadenas de texto en objetos Date
                Date fechaPedido = null;
                Date fechaEntrega = null;

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fechaPedido = dateFormat.parse(fechaPedidoStr);
                    fechaEntrega = dateFormat.parse(fechaEntregaStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Utiliza el ClienteDAO para obtener el cliente por su ID
                Cliente cliente = clienteDAO.obtenerClientePorId(clienteId);
                Menu menu=  menuDAO.obtenerMenuPorId(menuId);

                Pedido pedido = new Pedido(id, menu, cliente, fechaPedido, fechaEntrega, estado);
                pedidos.add(pedido);
            }
               
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido obtenerPedidoPorId(int pedidoId) {
    Pedido pedido = null;

    try {
        String query = "SELECT id, fecha_pedido, fecha_entrega, estado, cliente_id, menu_id FROM pedidos WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, pedidoId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fechaPedidoStr = resultSet.getString("fecha_pedido");
            String fechaEntregaStr = resultSet.getString("fecha_entrega");
            String estado = resultSet.getString("estado");
            int clienteId = resultSet.getInt("cliente_id");
            int menuId = resultSet.getInt("menu_id");

            // Convierte las cadenas de texto en objetos Date
            Date fechaPedido = null;
            Date fechaEntrega = null;

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fechaPedido = dateFormat.parse(fechaPedidoStr);
                fechaEntrega = dateFormat.parse(fechaEntregaStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Utiliza el ClienteDAO para obtener el cliente por su ID
            Cliente cliente = clienteDAO.obtenerClientePorId(clienteId);
            Menu menu = menuDAO.obtenerMenuPorId(menuId);

            pedido = new Pedido(id, menu, cliente, fechaPedido, fechaEntrega, estado);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pedido;
}

}

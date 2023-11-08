package com.mycompany.fikaproject;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public Route mostrarClientes = (Request request, Response response) -> {
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        return "Lista de Clientes: " + clientes.toString();
    };
}

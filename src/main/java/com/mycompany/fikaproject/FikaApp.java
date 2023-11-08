package com.mycompany.fikaproject;

import spark.template.velocity.VelocityTemplateEngine;

public class FikaApp {

    public static void main(String[] args) {
    // Crear una instancia de VelocityTemplateEngine
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
    
    // Crear una instancia de ClienteDAO, PedidoDAO, MenuDAO, ProductoDAO
    ClienteDAO clienteDAO = new ClienteDAO();
    MenuDAO menuDAO = new MenuDAO();
    PedidoDAO pedidoDAO = new PedidoDAO(DatabaseConnection.getConnection(), clienteDAO, menuDAO);
    ProductoDAO productoDAO = new ProductoDAO();
    // Crear una instancia de PedidoController y pasar el PedidoDAO
    PedidoController pedidoController = new PedidoController(pedidoDAO);
    MenuController menuController = new MenuController(menuDAO);
    ProductoController productoController = new ProductoController(productoDAO);
    // Crear una instancia de FikaAppController
    FikaAppController fikaAppController = new FikaAppController(pedidoController, menuController, productoController, velocityTemplateEngine);
    
    // Llamar al método init() en la instancia de FikaAppController
    fikaAppController.init();
}

}


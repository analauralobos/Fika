package com.mycompany.fikaproject;

import spark.template.velocity.VelocityTemplateEngine;

public class FikaApp {

    public static void main(String[] args) {
    
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
    
    
    UsuarioDAO clienteDAO = new UsuarioDAO();
    MenuDAO menuDAO = new MenuDAO();
    PedidoDAO pedidoDAO = new PedidoDAO(DatabaseConnection.getConnection(), clienteDAO, menuDAO);
    ProductoDAO productoDAO = new ProductoDAO();
    CarritoDAO carritoDAO = new CarritoDAO();
    UsuarioDAO usuarioDAO= new UsuarioDAO();
    
    PedidoController pedidoController = new PedidoController(pedidoDAO);
    MenuController menuController = new MenuController(menuDAO);
    ProductoController productoController = new ProductoController(productoDAO);
    CarritoController carritoController = new CarritoController(carritoDAO);
    UsuarioController usuarioController = new UsuarioController(usuarioDAO);
    
    FikaAppController fikaAppController = new FikaAppController(pedidoController, menuController,usuarioController, productoController,carritoController, velocityTemplateEngine);
    
    fikaAppController.init();
}

}


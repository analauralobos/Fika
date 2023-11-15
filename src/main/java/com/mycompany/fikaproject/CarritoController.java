package com.mycompany.fikaproject;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.template.velocity.VelocityTemplateEngine;

public class CarritoController {

    private CarritoDAO carritoDAO;
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public CarritoController(CarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }

    public Route mostrarCarrito = (Request request, Response response) -> {
        int clienteId = obtenerClienteIdDesdeSesion(request);
        Carrito carrito = carritoDAO.obtenerCarritoPorId(clienteId);

        Map<String, Object> model = new HashMap<>();
        model.put("carrito", carrito);
        if (carrito != null) {
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/carrito.vm"));
        } else {
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/carrito-vacio.vm"));
        }

    };

    public Route handleAgregarAlCarritoM = (Request request, Response response) -> {
        int menuId = Integer.parseInt(request.params(":id")); // Obtén el ID del menú desde los parámetros        
        System.out.println("CarritoController:menuId: " + menuId);
        int clienteId = obtenerClienteIdDesdeSesion(request);
        System.out.println("CarritoController:clienteId: " + clienteId);

        carritoDAO.agregarMenuAlCarrito(clienteId, menuId);

        response.redirect("/carrito");
        return "";

    };

    public Route handleAgregarAlCarritoP = (Request request, Response response) -> {

        int productoId = Integer.parseInt(request.params(":id"));
        System.out.println("CarritoController:productoId: " + productoId);
        int clienteId = obtenerClienteIdDesdeSesion(request);
        System.out.println("CarritoController:clienteId: " + clienteId);

        carritoDAO.agregarProductoAlCarrito(clienteId, productoId);

        response.redirect("/carrito");
        return "";

    };

    private int obtenerClienteIdDesdeSesion(Request request) {
        Session session = request.session();

        if (session.attribute("clienteId") != null) {
            return session.attribute("clienteId");
        } else {

            return -1;
        }
    }

}

package com.mycompany.fikaproject;

import java.util.HashMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class ProductoController {
    private ProductoDAO productoDAO;
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
    
    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public Route mostrarProductos = (Request request, Response response) -> {
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();

        Map<String, Object> model = new HashMap<>();
        model.put("productos", productos);

        return velocityTemplateEngine.render(new ModelAndView(model, "templates/productos.vm"));
    };

    
}

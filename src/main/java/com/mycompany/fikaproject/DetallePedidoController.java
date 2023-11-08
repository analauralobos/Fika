package com.mycompany.fikaproject;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

public class DetallePedidoController {

    private PedidoDAO pedidoDAO;
    private VelocityTemplateEngine velocityTemplateEngine;

    public DetallePedidoController(PedidoDAO pedidoDAO, VelocityTemplateEngine velocityTemplateEngine) {
        this.pedidoDAO = pedidoDAO;
        this.velocityTemplateEngine = velocityTemplateEngine;
    }

    public Route mostrarDetallePedido = (Request request, Response response) -> {
        int pedidoId = Integer.parseInt(request.params(":id")); // Obtiene el ID del pedido desde la URL
        Pedido pedido = pedidoDAO.obtenerPedidoPorId(pedidoId); // Obtén el pedido por su ID
        
        Map<String, Object> model = new HashMap<>();
        model.put("pedido", pedido);

        return velocityTemplateEngine.render(new ModelAndView(model, "templates/detalle-pedido.vm"));
    };
}

package com.mycompany.fikaproject;

import java.util.HashMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;

public class PedidoController {

    private PedidoDAO pedidoDAO;
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public PedidoController(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public Route mostrarPedidos = (Request request, Response response) -> {
        List<Pedido> pedidos = pedidoDAO.obtenerTodosLosPedidos();

        Map<String, Object> model = new HashMap<>();
        model.put("pedidos", pedidos);

        return velocityTemplateEngine.render(new ModelAndView(model, "templates/pedidos.vm"));
    };

    public Route mostrarDetallesPedido = (Request request, Response response) -> {
        int pedidoId = Integer.parseInt(request.params(":id")); // Obtén el ID del pedido desde los parámetros
        // Recupera el pedido y sus detalles 
        Pedido pedido = pedidoDAO.obtenerPedidoPorId(pedidoId);

        // Verifica si el pedido se encontró (puedes manejar el caso si no se encuentra)
        if (pedido != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("pedido", pedido);

            return velocityTemplateEngine.render(new ModelAndView(model, "templates/detalle-pedido.vm"));
        } else {
            response.status(404); // Devuelve un código de estado 404 si el pedido no se encontró
            return "Pedido no encontrado";
        }
    };
    

}

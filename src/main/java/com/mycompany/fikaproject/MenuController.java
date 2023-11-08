package com.mycompany.fikaproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;

public class MenuController {

    private MenuDAO menuDAO;
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public MenuController(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public Route mostrarMenus = (Request request, Response response) -> {
        List<Menu> menu = menuDAO.obtenerTodosLosMenus();

        Map<String, Object> model = new HashMap<>();
        model.put("menus", menu);

        return velocityTemplateEngine.render(new ModelAndView(model, "templates/menu.vm"));
    };
    
    public Route mostrarFormularioCreacionMenu = (Request request, Response response) -> {
        List<Producto> productosDisponibles = menuDAO.obtenerTodosLosProductos(); // Recupera los productos de la base de datos
        Map<String, Object> model = new HashMap<>();
        model.put("productos", productosDisponibles); // Agrega la lista de productos al modelo
        return velocityTemplateEngine.render(new ModelAndView(model, "templates/crear-menu.vm"));
    };

    public Route crearMenu = (Request request, Response response) -> {
        
        // Obtén los datos del menú enviados por el usuario desde 'request'
        String nombreMenu = request.queryParams("nombreMenu");
        String descripcion = request.queryParams("descripcion");
        double descuento = Double.parseDouble(request.queryParams("descuento"));
        double aumento = Double.parseDouble(request.queryParams("aumento"));

        // Obtén los productos seleccionados
        String[] productosIds = request.queryParamsValues("productos[]");
        System.out.println("pr: " + Arrays.toString(productosIds));
        String[] cantidades = request.queryParamsValues("cantidadProductos[]");
        System.out.println("cc: " + Arrays.toString(cantidades));
        // Filtra los productos con cantidades mayores a cero
        List<String> cantidadesP = new ArrayList<>();        
        List<Producto> productosSeleccionados = new ArrayList<>();
        
        for (int i = 0; i < cantidades.length; i++) {
            int cantidad = Integer.parseInt(cantidades[i]);
            if (cantidad > 0) {
                cantidadesP.add(cantidades[i]);
            }
        }
        System.out.println("cc:" + cantidadesP);
        
        // Procesa los productos seleccionados 
        for (int i = 0; i < productosIds.length; i++) {
            int idProducto = Integer.parseInt(productosIds[i]);
            System.out.println("menuController:idProd: " + idProducto);
            Producto producto = menuDAO.obtenerProductoPorId(idProducto);
            productosSeleccionados.add(producto);
        }

        System.out.println("menuController: Aumento: " + aumento + "Descuento: " + descuento);
        
        // Calcula el precio total del menú basado en los productos, descuento y aumento
        double precioTotal = menuDAO.calcularPrecioTotalMenu(productosSeleccionados, descuento, aumento, cantidadesP);

        // Crea un nuevo menú con los datos proporcionados
        Menu nuevoMenu = new Menu(nombreMenu, descripcion, productosSeleccionados, descuento, aumento, precioTotal);

        // Agrega el nuevo menú al DAO de menús
        menuDAO.agregarMenu(nuevoMenu, cantidadesP);

        // Redirecciona al usuario a la página de menús o muestra una página de confirmación
        response.redirect("/menu-creado");
        return "";
    };


    public Route mostrarDetallesMenu = (Request request, Response response) -> {
        int menuId = Integer.parseInt(request.params(":id")); // Obtén el ID del menú desde los parámetros

        // Recupera el menú y sus detalles (asegúrate de que tu MenuDAO tenga un método para esto)
        Menu menu = menuDAO.obtenerMenuPorId(menuId);

        // Verifica si el menú se encontró (puedes manejar el caso si no se encuentra)
        if (menu != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("menu", menu);

            return velocityTemplateEngine.render(new ModelAndView(model, "templates/detalle-menu.vm"));
        } else {
            response.status(404); // Devuelve un código de estado 404 si el menú no se encontró
            return "Menú no encontrado";
        }
    };

}

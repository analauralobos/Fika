package com.mycompany.fikaproject;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public class FikaAppController {

    private PedidoController pedidoController;
    private MenuController menuController;
    private ProductoController productoController;
    private CarritoController carritoController;
    private UsuarioController usuarioController;
    private VelocityTemplateEngine velocityTemplateEngine;

    public FikaAppController(PedidoController pedidoController, MenuController menuController, UsuarioController usuarioController, ProductoController productoController,CarritoController carritoController ,VelocityTemplateEngine velocityTemplateEngine) {
        this.pedidoController = pedidoController;
        this.menuController = menuController;
        this.productoController = productoController;
        this.carritoController= carritoController;
        this.velocityTemplateEngine = velocityTemplateEngine;
        this.usuarioController = usuarioController;
    }

    public void init() {
        // Configurar rutas y controladores
        Spark.get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("mensaje", "¡Bienvenido a Fika Café!");
            // Renderizar la plantilla HTML home.vm utilizando el motor de plantillas Velocity
            
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/home.vm"));
            
        });

        Spark.get("/pedidos", (req, res) -> {
            return pedidoController.mostrarPedidos.handle(req, res);
        });
        Spark.get("/pedido/:id", (req, res) -> {
            return pedidoController.mostrarDetallesPedido.handle(req, res);
        });

        Spark.get("/productos", (req, res) -> {
            return productoController.mostrarProductos.handle(req, res);
        });
        
        Spark.get("/productos-cliente", (req, res) -> {
            return productoController.mostrarProductosCliente.handle(req, res);
        });
        
        Spark.get("/productos-admin", (req, res) -> {
            return productoController.mostrarProductosAdmin.handle(req, res);
        });
        
        Spark.get("/menu", (req, res) -> {
            return menuController.mostrarMenus.handle(req, res);
        });
        Spark.get("/menu/:id", (req, res) -> {
            return menuController.mostrarDetallesMenu.handle(req, res);
        });
        Spark.get("/menu-cliente/:id", (req, res) -> {
            return menuController.mostrarDetallesMenuC.handle(req, res);
        });
        
        Spark.get("/menu-cliente", (req, res) -> {
            return menuController.mostrarMenusCliente.handle(req, res);
        });

        Spark.get("/menu-admin/:id", (req, res) -> {
            return menuController.mostrarDetallesMenuA.handle(req, res);
        });
        
        Spark.get("/menu-admin", (req, res) -> {
            return menuController.mostrarMenusAdmin.handle(req, res);
        });
        
        Spark.get("/crear-menu", (req, res) -> {
            return menuController.mostrarFormularioCreacionMenu.handle(req, res);
        });

        Spark.post("/crear-menu", (req, res) -> {
            return menuController.crearMenu.handle(req, res);
        });
        
        Spark.get("/menu-creado", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/menu-creado.vm"));
        });
        
     
        
        Spark.get("/carrito", (req, res) -> {
            return carritoController.mostrarCarrito.handle(req, res);
        });
        
        Spark.get("/carrito/:id", (req, res) -> {
            return carritoController.handleAgregarAlCarritoM.handle(req, res);
        });
        
        Spark.get("/carrito-p/:id", (req, res) -> {
            return carritoController.handleAgregarAlCarritoP.handle(req, res);
        });
        
        
        
        Spark.get("/carrito-vacio", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/carrito-vacio.vm"));
        });
        
        
        
        
        Spark.get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/login.vm"));
        });
        
        Spark.post("/login", (req, res) -> {
            return usuarioController.handleLogin.handle(req, res);
        });
        
        
        Spark.post("/logout", (req, res) -> {
            return usuarioController.handleLogout.handle(req, res);
        });
        
        Spark.get("/home-admin", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/home-admin.vm"));
        });
        Spark.get("/home-usuario", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/home-usuario.vm"));
        });
        
        Spark.get("/signup", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/signup.vm"));
        });
        Spark.post("/signup", (req, res) -> {
            return usuarioController.crearUsuario.handle(req, res);
        });
        Spark.get("/usuario-creado", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return velocityTemplateEngine.render(new ModelAndView(model, "templates/usuario-creado.vm"));
        });
    }
}

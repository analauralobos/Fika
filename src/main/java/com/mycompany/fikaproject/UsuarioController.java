package com.mycompany.fikaproject;

import static java.lang.Integer.parseInt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO clienteDAO) {
        this.usuarioDAO = clienteDAO;
    }

    public Route mostrarClientes = (Request request, Response response) -> {
        List<Usuario> clientes = usuarioDAO.obtenerTodosLosUsuarios();
        return "Lista de Clientes: " + clientes.toString();
    };

    public Route handleLogin = (Request request, Response response) -> {
        
        System.out.println("HandleLogin");
        String email = request.queryParams("email");
        String contraseña = request.queryParams("contraseña");
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCredenciales(email, contraseña);
        
        
        if (usuario != null) {
            System.out.println("Login:ROL: " + usuario.getRol());
            System.out.println("Login:IdUsuario: " + usuario.getId());
            request.session().attribute("usuario", usuario);
            request.session().attribute("clienteId", usuario.getId());
            String rol = usuario.getRol();

            if ("ADMIN".equals(rol)) {
                response.redirect("/home-admin");
            } else if ("CLIENTE".equals(rol)) {
                response.redirect("/home-usuario");
            } 
        } else {
            response.redirect("/signup");
        }

        return null;
    };
    
    public Route handleLogout = (Request request, Response response) -> {
        request.session().removeAttribute("usuario");
        response.redirect("/");
        return null;
    };

    public Route crearUsuario = (Request request, Response response) -> {
        System.out.println("ingresarUsuario");
        String nombre = request.queryParams("nombre");
        String email = request.queryParams("email");
        String telefono = request.queryParams("telefono");
        String contraseña = request.queryParams("contraseña");
        int edad = parseInt(request.queryParams("edad"));
        usuarioDAO.agregarCliente(nombre,email,telefono,edad,contraseña);
        response.redirect("/usuario-creado");
        return null;
    };
    
    
}

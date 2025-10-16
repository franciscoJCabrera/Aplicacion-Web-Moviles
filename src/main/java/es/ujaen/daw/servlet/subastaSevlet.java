package es.ujaen.daw.servlet;

import es.ujaen.daw.Util;
import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.enity.movil;
import es.ujaen.daw.model.UsuarioDao;
import es.ujaen.daw.model.movilDao;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import es.ujaen.daw.qualifiers.movilJPA;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// @WebServlet(name = "subasta", value = "/subasta")
@WebServlet("/subasta" )
public class subastaSevlet extends HttpServlet {

    private String srvUrl;
    private final Logger logger = Logger.getLogger(subastaSevlet.class.getName());

    @Inject @movilJPA
    private movilDao database;

    @Inject @UsuarioJPA
    private UsuarioDao userDataBase;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        logger.info("Inicializando subastaSevlet");

        //Servlet and image dir URLs for views' use
        srvUrl = servletConfig.getServletContext().getContextPath() + "/subasta";

    }

    /**
     * Common Request processing
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Expires", "0"); //Avoid browser caching response

        request.setAttribute("srvUrl", srvUrl);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Comprobamos si esta identificado, si no lo esta, restringimos el acceso
        if(request.isUserInRole("USUARIOS") || request.isUserInRole("ADMINISTRADORES")) {

            processRequest(request, response);
            RequestDispatcher rd;

            //Detect current servlet action
            String action = (request.getPathInfo() != null ? request.getPathInfo() : "");

            logger.log(Level.INFO, "Petición GET {0}", action);
            int id = Integer.parseInt(request.getParameter("id"));

            movil phone = database.buscaId(id);
            Usuario user = userDataBase.buscaId( phone.getCorreoUsuario() );

            // Si la subasta es del propio usuario identificado, enviamos a la vista de ver subastas
            if( request.getRemoteUser().equals( phone.getCorreoUsuario() ) ) {
                response.sendRedirect(request.getContextPath() + "/index.xhtml");
            } else {

                // Le damos una visita a la subasta y la guardamos
                phone.addVisita();
                database.guarda(phone);

                request.setAttribute("movil", phone);
                request.setAttribute("user", user);

                rd = request.getRequestDispatcher("/subasta/subasta.jsp");
                rd.forward(request, response);
            }
        }
        // Restringe el acceso
        else {
            response.sendRedirect(request.getContextPath() + "/usuario/PaginaInicioSesion.xhtml");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.log(Level.INFO, "Se ha proporcionado una puja");

        // Validamos que la puja dada sea mayor a la que ya había
        int id = Integer.parseInt(Util.getParam(request.getParameter("id"), "0"));
        movil phone = database.buscaId(id);
        int precio = Integer.parseInt(Util.getParam(request.getParameter("precioProporcionado"), "0"));

        String formulario = request.getParameter("formulario");

        // Vemos si el usuario ha usado el formulario de subasta o de compra
        if (formulario.equals("formularioSubasta")) {

            // Si la puja es menor que la que tiene el movil, damos un error
            if( precio <= phone.getPrecioActual() ) {
                request.setAttribute("errPrecio", "Precio de puja no valido");
            }
            // Si el precio de la puja es mayor al precio de compra, hacemos que el precio de compra sea el de la subasta
            else if( precio >= phone.getPrecioCompra() ) {
                phone.setPrecioActual(precio);
                phone.setPrecioCompra( phone.getPrecioActual() );
            }
            // Puja normal
            else {
                phone.setPrecioActual(precio);
            }

            // Guardamos cambios
            database.guarda(phone);

            // Hacemos la redireccion para ver los cambios en la subasta
            processRequest(request, response);
            RequestDispatcher rd;

            request.setAttribute("movil", phone);

            response.sendRedirect(request.getContextPath() + "/subasta/subasta.jsp");

//            rd = request.getRequestDispatcher("/subasta/subasta.jsp");
//            rd.forward(request, response);

        } else if (formulario.equals("formularioCompra")) {

            // Marcamos el movil como comprado
            phone.setComprado();

            // Guardamos el movil en el usuario
            database.guardaMovilUsuario(phone, request.getRemoteUser());

            // Hacemos la redireccion para que el usuario vea el movil que ha comprado
            processRequest(request, response);

            response.sendRedirect(request.getContextPath() + "/usuario/PaginaPerfil.xhtml");
        }
    }
}
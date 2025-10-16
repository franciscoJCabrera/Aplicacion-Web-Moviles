package es.ujaen.daw.webServices;

import es.ujaen.daw.controller.busquedaController;
import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.model.UsuarioDao;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("usuario")
@Produces (MediaType.APPLICATION_JSON)
@RequestScoped
public class usuarioRESTService {

    private final Logger logger = Logger.getLogger(busquedaController.class.getName());

    @Inject @UsuarioJPA
    private UsuarioDao usuarioDAO;

    @GET
    public List<Usuario> listado(){
        return usuarioDAO.buscaTodos();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoUsuario( @Valid Usuario usuario){
        usuarioDAO.crea(usuario);
        return Response.ok(usuario).build();
    }

}

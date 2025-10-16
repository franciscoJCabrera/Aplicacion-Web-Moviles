package es.ujaen.daw.webServices;

import es.ujaen.daw.controller.busquedaController;
import es.ujaen.daw.enity.movil;
import es.ujaen.daw.model.movilDao;
import es.ujaen.daw.qualifiers.movilJPA;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.primefaces.PrimeFaces;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("movil")
@Produces(MediaType.APPLICATION_JSON)

@RequestScoped
public class movilRESTService {

    @Context
    private UriInfo context;
    private final Logger logger = Logger.getLogger(busquedaController.class.getName());

    @Inject @movilJPA
    movilDao database;

    @GET
    public List<movil> getSubastas() {
        return database.buscaTodos();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevaSubasta(@Valid movil m) {
        // logger.log(Level.INFO, m.toString());
        database.crea(m);
        return Response.ok(m).build();
    }

    @POST
    @Path("/formSubasta")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update_or_createSubasta(@Valid movil m) {

        if (!this.database.exists(m.getId())) {

            this.database.crea(m);

        } else {

            this.database.guarda(m);
        }

        return Response.ok(m).build();
    }

}

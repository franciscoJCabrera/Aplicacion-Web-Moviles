package es.ujaen.daw.controller;

import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.model.UsuarioDAOMap;
import es.ujaen.daw.model.UsuarioDao;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named(value = "RegistroControl")
//@ViewScoped
@SessionScoped
public class RegistroController implements Serializable {

    private Usuario usuario;
    @Inject @UsuarioJPA
    private UsuarioDao database;

    private List<String> listadoPaises;
        private List<String> listadoCiudades;
    private String ciudad;
    private String codPostal;
    private final Logger logger = Logger.getLogger(RegistroController.class.getName());


    /**
     * Inicia las listas de datos una vez se ha realizado la inyeccion del DAO
     */
    @PostConstruct
    public void init(){
       this.usuario = new Usuario();
       this.listadoPaises = database.devolverPaises();
       this.listadoCiudades = new ArrayList<>();
    }

    ///Declaramos los getter y setter

    public Usuario getUsuario() {
        return usuario;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String GuardarCambios(){
        try {
            // Lógica para guardar los cambios

            // Vemos que no existe ya un usuario con esa id
            if( database.buscaId(usuario.getCorreoElectronico()) != null ) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ya existe un usuario con ese correo electrónico", ""));
                return "";
            } else {

                database.crea(usuario);
            }
//            // Redireccionar a la vista del perfil del usuario
//            FacesContext context = FacesContext.getCurrentInstance();
//            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
//            navigationHandler.handleNavigation(context, null, "PaginaPerfil?faces-redirect=true");
            return "PaginaPerfil?faces-redirect=true";
        } catch (Exception exception){

            // Errores

            logger.log(Level.SEVERE, exception.getMessage(), exception);
            return "";
        }
    }

    public List<String> getListadoPaises() {
        return listadoPaises;
    }

    public void actualizarListadoCiudades(){
        this.listadoCiudades = database.devolverCiudades(usuario.getPais());
    }

    public void setListadoPaises(List<String> listadoPaises) {
        this.listadoPaises = listadoPaises;
    }

    public List<String> getListadoCiudades() {
        return listadoCiudades;
    }

    public void setListadoCiudades(List<String> listadoCiudades) {
        this.listadoCiudades = listadoCiudades;
    }

    public List<String> listadoPaises() {
        return listadoPaises;
    }
    public List<String> listadoCiudades() {
        return listadoPaises;
    }
}

package es.ujaen.daw.controller;


import es.ujaen.daw.enity.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "InicioSesionControl")
@ViewScoped
public class InicioSesionController implements Serializable {

    ///Se hacen las injecciones
    @Inject
    FacesContext fc;
    @Inject
    SecurityContext sc; ///JEE security API access
    @Inject
    ExternalContext ec; ///JEE web server
    @Inject HttpServletRequest req; ///Current http request

    ///Logger
    private final Logger logger = Logger.getLogger(InicioSesionController.class.getName());


    private Usuario usuario;
    private Boolean recordarDatos;

    @PostConstruct
    public void init(){
        this.usuario = new Usuario();
    }

    public Boolean getRecordarDatos() {
        return recordarDatos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setRecordarDatos(Boolean recordarDatos) {
        this.recordarDatos = recordarDatos;
    }

    public String botonIniciarSesion(){
        ///Cuando iniciemos sesion nos va a redireccionar a la pagina que le indiquemos aqui
        return "PaginaPerfil?faces-redirect=true";
    }

    public String login(){
        String view="";

        AuthenticationParameters ap = new AuthenticationParameters();
        Credential credential = new UsernamePasswordCredential(usuario.getCorreoElectronico(), usuario.getContrasenia());
        ap.credential(credential).newAuthentication(true);

        HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        if (sc.authenticate(req, response, ap) == AuthenticationStatus.SUCCESS){
            if (req.isUserInRole("ADMINISTRADORES")){
                view = "/index?faces-redirect=true";
            }else{
                view = "/index?faces-redirect=true";
            }
            logger.log(Level.INFO, "Usuario autenticado");
        } else {
            fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de autenticacion", ""));
            logger.log(Level.WARNING, "Error de autenticacion");
        }
        return view;
     }




    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

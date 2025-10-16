package es.ujaen.daw.controller;

import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.enity.movil;
import es.ujaen.daw.model.UsuarioDao;
import es.ujaen.daw.model.movilDao;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import es.ujaen.daw.qualifiers.movilJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Named (value = "PerfilControl")
///No es un View ya que no vamos a utilizar injects
@RequestScoped
public class PerfilController {

    ///Definimos las distintas variables que podemos encontrarnos en esta vista

    @Inject @UsuarioJPA
    private UsuarioDao database;

    @Inject @movilJPA
    private movilDao movilUsuario;

    private Usuario usuario;
    private String nombre;
    private String apellidos;
    private String dni;
    private String correoElectronico;
    private int numeroTelefono;
    private String domicilio;

    ///Se hacen las injecciones
    @Inject
    FacesContext fc;
    @Inject
    SecurityContext sc; ///JEE security API access
    @Inject
    ExternalContext ec; ///JEE web server
    @Inject HttpServletRequest req; ///Current http request




    @PostConstruct
    public void init(){

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        this.usuario = database.buscarUsuarioCorreo( request.getRemoteUser() );
        // this.usuario = new Usuario();
        this.apellidos = "Cabrera Bermejo";
        this.dni = "26502929J";
        this.correoElectronico = "fjcb0015@red.ujaen.es";
        this.numeroTelefono = 603544484;
        this.domicilio = "Avenida de Granda Numero 22";
    }



    ///Definimos los getters and setters
    public String getNombre() {
        return nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void guardarCambios(){
        database.guarda(usuario);
    }

    public String borrarUsuario() throws ServletException {

        // Borramos las subastas del usuario
        movilUsuario.borraSubastasUsuario();

        // Borramos el usuario
        database.borra(usuario.getCorreoElectronico());
        return logout();
    }

    /**
     * Metodo que sirve para hacer abandonar la sesion
     * @return
     * @throws ServletException
     */
    public String logout() throws ServletException {
        req.logout();
        req.getSession().invalidate();
        ec.invalidateSession();
        return "/index?faces-redirect=true";
    }

    public List<movil> obtenerMovilesUsuario(){
        return movilUsuario.getMovilesUsuario();
    }

    public List<movil> obtenerSubastasUsuario(){
        return movilUsuario.getSubastasUsuario();
    }


}

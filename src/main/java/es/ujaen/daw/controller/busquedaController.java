package es.ujaen.daw.controller;


import es.ujaen.daw.model.imageDAO;
import es.ujaen.daw.model.moviMapDAO;
import es.ujaen.daw.enity.movil;
import es.ujaen.daw.model.movilDao;
import es.ujaen.daw.qualifiers.imageDatabase;
import es.ujaen.daw.qualifiers.imageLocal;
import es.ujaen.daw.qualifiers.movilJPA;
import es.ujaen.daw.qualifiers.movilMap;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.primefaces.model.StreamedContent;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "busquedaCtrl")
@SessionScoped
public class busquedaController implements Serializable {

    private final Logger logger = Logger.getLogger(busquedaController.class.getName());
    @Inject
    FacesContext fc;
    @Inject @movilJPA
    private movilDao database;

    @Inject @imageDatabase
    private imageDAO imagenesDB;

    private movil phoneToSearch = new movil();
    private List<String> listaMarcas; // Lista que contiene todas las distintas marcas de telefonos
    private List<String> listaModelos; // Lista con los modelos de telefonos de esas marcas, se cargan al seleccionar la marca
    private List<String> listaCapacidadCarga; // Lista con todas las velocidades de carga disponibles
    private List<String> listaTecnologiaPantalla; // Lista con todas las tecnologías de pantallas (como LCD, OLED, ...)
    private List<String> listaTasaRefresco; // Lista con todas las tasas de refresco de pantalla que tenemos disponible
    private List<String> listaCapacidadAlmecenamiento; // Lista con todas las capacidades de almacenamiento secundario
    private List<String> listaCapacidadRAM; // Lista con todas las capacidades de RAM

    private List<movil> resultados; // Lista donde se almacenan los resultados de la busqueda en la BBDD
    private List<movil> moviles_destacados; // Lista de subastas que son destacadas, para mostrar en la página principal
    @Min(value = 0, message = "La capacidad de la batería no puede ser negativa")
    private int capacidadBateria; // Capacidad de batería que tiene el móvil a buscar, de esta forma evitamos el bean controller
    @Min(value = 0, message = "El número de procesadores no puede ser negativo")
    private int numCpus; // Numero de cpus que tiene el móvil a buscar, de esta forma evitamos el bean controller
    @Min(value = 0, message = "El precio de venta buscado no puede ser negativo")
    private int precioVentaPublico; // El precio de venta del móvil a buscar, de esta forma evitamos el bean controller

    private String correoUsuario;

    /**
     * Inicia las listas de datos una vez se ha realizado la inyeccion del DAO
     */
    @PostConstruct
    public void init() {

        // Inicializacion
        this.listaMarcas = database.getListaMarcas();
        this.listaModelos = new ArrayList<>();
        this.listaCapacidadCarga = database.getListaCargas();
        this.listaTecnologiaPantalla = database.getListaPantallas();
        this.listaTasaRefresco = database.getListaRefresco();
        this.listaCapacidadAlmecenamiento = database.getListaCapacidadesAlmacenamiento();
        this.listaCapacidadRAM = database.getListaCapacidadRAM();
        this.moviles_destacados = database.getListaDestacados();
    }

    /**
     * Busca los moviles que se tienen disponibles con los datos introducidos por el usuario
     * y muestralos en la ventana de visualiza
     * @return URL de la página de vista sin ?faces-redirect para que no se borren los moviles buscados
     */
    public String buscar() {

        // Buscamos el movil con los datos buscados dentro de la BBDD

        this.phoneToSearch.setCapacidadBateria( capacidadBateria );
        this.phoneToSearch.setNumProcesadores(numCpus);
        this.phoneToSearch.setPrecioCompra( precioVentaPublico );
        this.phoneToSearch.setCorreoUsuario( correoUsuario );
        this.resultados = database.buscarMovil( phoneToSearch );

        logger.log(Level.INFO, "Busqueda realizada con el movil de estas caracteristicas: \n" + phoneToSearch.toString());

        // Si hay moviles y hay sesión iniciada, borramos las subastas del mismo usuario
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String correoUsuario = request.getRemoteUser();
        if( correoUsuario != null ) {
            List<movil> resultadosSinUsuario = new ArrayList<>();
            // Eliminamos aquellos resultados que sean del propio usuario
            for (movil resultado: this.resultados) {
                if( !resultado.getCorreoUsuario().equals( correoUsuario ) ) {
                    resultadosSinUsuario.add(resultado);
                }
            }
            this.resultados = resultadosSinUsuario;
        }

        // Si no hay resultados, mostramos un mensaje de error
        if( resultados.isEmpty() ) {

            fc.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,"No existen subastas con los filtros proporcionados", "No existen subastas con los filtros proporcionados"));

            return "";
        }

        //Post-Redirect-Get
        return "resultados_busqueda?faces-redirect=true";

    }

    /**
     * Dado un movil, devuelve una lista con los contenidos de imagenes de dicho movil
     * @param subasta subasta de la que se quieren obtener las imagenes
     * @return Una lista con los contenidos de las imagenes o lista vacia si no hay imagenes de dicho movil
     */
    public List<StreamedContent> obtenImagenesSubasta(movil subasta) {

        return imagenesDB.buscaIdMovil( subasta.getId() );

    }

    public List<StreamedContent> obtenImagenesSubasta(int id) {

        return imagenesDB.buscaIdMovil( id );

    }
    public List<String> getListaMarcas() {
        this.listaMarcas = database.getListaMarcas();
        return listaMarcas;
    }

    /**
     * Lleva a la subasta del movil que se pida
     * @param phone Movil al que llevar de subasta
     */
    public void redirectSubasta(movil phone) throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String url = context.getExternalContext().getRequestContextPath() + "/subasta";
            // Agregar parámetros a la URL
            url += "?id=" + phone.getId();

            context.getExternalContext().redirect(url);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "No se ha podido realizar la redirección al jsp de subasta");
        }
    }

    public List<movil> getMoviles_destacados() {
        this.moviles_destacados = database.getListaDestacados();
        return moviles_destacados;
    }

    public void setMoviles_destacados(List<movil> moviles_destacados) {
        this.moviles_destacados = moviles_destacados;
    }

    /**
     * Cargamos los modelos dependiendo de la marca
     * @return Lista de los modelos que tenemos disponibles de esa marca
     */
    public List<String> getListaModelos() {
        return listaModelos;
    }

    /**
     * Actualiza la lista de modelos disponibles cuando se selecciona una marca concreta
     */
    public void actualizaListaModelos() {
        this.listaModelos = database.getListaModelo(phoneToSearch.getMarca() );
    }

    /**
     * Obten el número de moviles a mostrar en la vista de resultados de moviles
     * @return
     */
    public int numRows() {
        return 10;
    }

    public List<String> getListaCapacidadCarga() {
        this.listaCapacidadCarga = database.getListaCargas();
        return listaCapacidadCarga;
    }

    public List<String> getListaTecnologiaPantalla() {
        this.listaTecnologiaPantalla = database.getListaPantallas();
        return listaTecnologiaPantalla;
    }

    public List<String> getListaTasaRefresco() {
        this.listaTasaRefresco = database.getListaRefresco();
        return listaTasaRefresco;
    }

    public List<String> getListaCapacidadAlmecenamiento() {
        this.listaCapacidadAlmecenamiento = database.getListaCapacidadesAlmacenamiento();
        return listaCapacidadAlmecenamiento;
    }

    public List<String> getListaCapacidadRAM() {
        this.listaCapacidadRAM = database.getListaCapacidadRAM();
        return listaCapacidadRAM;
    }

    public List<movil> getResultados() {
        return resultados;
    }

    public void setResultados(List<movil> resultados) {
        this.resultados = resultados;
    }

    public movil getPhoneToSearch() {
        return phoneToSearch;
    }

    public int getCapacidadBateria() {
        return capacidadBateria;
    }

    public int getNumCpus() {
        return numCpus;
    }
    public int getPrecioVentaPublico() {
        return precioVentaPublico;
    }

    public void setCapacidadBateria(int capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public void setNumCpus(int numCpus) {
        this.numCpus = numCpus;
    }
    public void setPrecioVentaPublico(int precioVentaPublico) {
        this.precioVentaPublico = precioVentaPublico;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}

package es.ujaen.daw.controller;

import es.ujaen.daw.enity.movil;
import es.ujaen.daw.model.imageDAO;
import es.ujaen.daw.model.movilDao;
import es.ujaen.daw.qualifiers.imageDatabase;
import es.ujaen.daw.qualifiers.movilJPA;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Named
@ViewScoped
public class CrudView implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<movil> moviles;

    private movil selectedmovil;

    private List<movil> selectedmoviles;

    private boolean deleteOldImages; // Determina si borramos las imagenes que la subasta tenia antiguamente o no
    private boolean addingPhone; // Determina si se esta añadiendo un telefono (true) o se esta editando (false)

    private final Logger logger = Logger.getLogger(CrudView.class.getName());


    @Inject @movilJPA
    private movilDao database;

    @Inject @imageDatabase
    private imageDAO imagen_database;

    @PostConstruct
    public void init() {
        this.moviles = this.database.getSubastasUsuario();
        if(this.moviles == null)
            this.moviles = new ArrayList<>();
        this.selectedmoviles = new ArrayList<>();
        this.selectedmovil = new movil();
    }
    public void openNew() {
        this.addingPhone = true;
        this.selectedmovil = new movil();
    }


    public void editingPhone() {
        this.addingPhone = false;
    }

    public List<movil> getMoviles() {
        return moviles;
    }

    public void setMoviles(List<movil> moviles) {
        this.moviles = moviles;
    }

    public movil getSelectedmovil() {
        return selectedmovil;
    }

    public void setSelectedmovil(movil selectedmovil) {
        this.selectedmovil = selectedmovil;
    }

    public List<movil> getSelectedmoviles() {
        return selectedmoviles;
    }

    public void setSelectedmoviles(List<movil> selectedmoviles) {
        this.selectedmoviles = selectedmoviles;
    }

    /**
     * Guarda el movil creado dentro de la bbdd o los cambios si solamente se ha modificado
     */
    public void saveMovil() {

        // Borra las imagenes anteriores si asi se pide
        if (deleteOldImages) {
            imagen_database.borraTodas(selectedmovil.getId());
        }

        if (!this.database.exists(selectedmovil.getId())) {

            this.database.crea(selectedmovil);
            this.moviles.add(this.selectedmovil);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movil añadido"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movil actualizado"));
            this.database.guarda(selectedmovil);
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");


    }


    /**
     * Borra un movil de la base de datos
     */
    public void deleteMovil() {
        this.database.borra( this.selectedmovil.getId() );
        this.moviles.remove(this.selectedmovil);
        this.selectedmoviles.remove(this.selectedmovil);
        this.selectedmovil = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movil eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMoviles()) {
            int size = this.selectedmoviles.size();
            return size > 1 ? size + " Moviles seleccionados" : "1 movil seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSelectedMoviles() {
        return this.selectedmoviles != null && !this.selectedmoviles.isEmpty();
    }

    public void deleteSelectedMoviles() {

        // Borra todos los moviles selecccionados de la bbdd
        List<Integer> listaIds = new ArrayList<>();
        for( movil phone : selectedmoviles ) {
            int id = phone.getId();
            this.database.borra(id);
        }

        this.moviles.removeAll(this.selectedmoviles);
        this.selectedmoviles = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Moviles eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    /**
     * Método encargado de gestionar las imágenes subidas por el usuario, guardarlas en el directorio raíz y almacenar las rutas en la bbdd
     * @param files Archivos que se pretenden subir
     */
    public void gestionaImagenes( FileUploadEvent files ) {

        // Borra las imagenes anteriores si asi se pide
        if( deleteOldImages ) {
            imagen_database.borraTodas( selectedmovil.getId() );
        }
        imagen_database.crea( files, selectedmovil );

    }


    /**
     * Dado el id de un movil, devuelve una lista con los nombres de las imagenes de dicho movil
     * @param phone Movil cuyas imagenes se quieren buscar
     * @return Lista que contiene los contenidos de las imagenes del movil
     */
    public List<StreamedContent> obtenerImagenes( movil phone ) {

        return imagen_database.buscaIdMovil(phone.getId());
    }


    public boolean isDeleteOldImages() {
        return deleteOldImages;
    }

    public void setDeleteOldImages(boolean deleteOldImages) {
        this.deleteOldImages = deleteOldImages;
    }

    public boolean isAddingPhone() {
        return addingPhone;
    }
}
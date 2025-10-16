package es.ujaen.daw.model;

import es.ujaen.daw.enity.movil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import java.util.List;

/**
 * Definición general que debe de tener un DAO de imagenes
 */
public interface imageDAO {

    /**
     * Dada la id de una subasta, devuelve una lista de la informacion de dicha subasta
     * @param id Id del movil a buscar
     * @return Una lista que contiene todas las rutas de las imagenes de dicha subasta o null si no existen imagenes para esa subasta
     */
    public List<StreamedContent> buscaIdMovil(Integer id);

    /**
     * Dada una imagen, almacenala (el lugar de almacenaje depende de la implementación)
     * @param imagen Evento que contiene todas las imágenes
     * @param phone Movil al que se le va a asociar la imagen
     * @return {@code true} Si se ha podido guardar la imagen correctamente, {@code false} en otro caso
     */
    public boolean crea(FileUploadEvent imagen, movil phone);

    /**
     * Borra todas las imágenes asociadas a un teléfono
     * @param phone_id id del movil a las que las imagenes a borrar se encuentran asociadas
     * @return {@code true} Si se han podido borrar bien todas las imagenes asociadas a ese movil, {@code false} en otro caso
     */
    public boolean borraTodas(Integer phone_id);
}

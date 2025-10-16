package es.ujaen.daw.model;

import es.ujaen.daw.enity.movil;
import es.ujaen.daw.imagenesSubasta;

import es.ujaen.daw.qualifiers.imageDatabase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de gestionar las imagenes de las subastas
 * Guarda las imagenes dentro de la base de datos
 */
@Transactional
@RequestScoped
@imageDatabase
public class imageDAOdatabase implements imageDAO {

    @PersistenceContext
    private EntityManager em;
    private final Logger logger = Logger.getLogger(imageDAOdatabase.class.getName());

    /**
     * Dada la id de una subasta, devuelve una lista de la informacion de dicha subasta
     * @param id Id del movil a buscar
     * @return Una lista que contiene todas las rutas de las imagenes de dicha subasta o null si no existen imagenes para esa subasta
     */
    @Override
    public List<StreamedContent> buscaIdMovil(Integer id) {
        try {

            String plantillaSQL = "select i from imagenesSubasta i where i.id_phone=:id";
            TypedQuery<imagenesSubasta> query = em.createQuery(plantillaSQL, imagenesSubasta.class)
                    .setParameter("id", id);

            List<imagenesSubasta> imagenes = query.getResultList();
            List<StreamedContent> contents = new ArrayList<>();

            for(imagenesSubasta imagen : imagenes) {
                byte[] informacion = imagen.getImagen();
                String contentType = imagen.getContentType();
                String name = imagen.getFileName();

                StreamedContent content = DefaultStreamedContent.builder()
                        .contentType(contentType)
                        .stream(() -> new ByteArrayInputStream(informacion))
                        .name(name)
                        .build();
                contents.add(content);
            }
            return contents;

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Dada una imagen, almacenala dentro de la base de datos
     * @param imagen Evento que contiene todas las imágenes
     * @param phone Movil al que se le va a asociar la imagen
     * @return {@code true} Si se ha podido guardar la imagen correctamente, {@code false} en otro caso
     */
    @Override
    public boolean crea(FileUploadEvent imagen, movil phone) {

        try {
            int id_phone = phone.getId();
            UploadedFile file = imagen.getFile();
            byte[] informacion = file.getContent();
            String contenType = file.getContentType();
            String fileName = file.getFileName();

            imagenesSubasta imagenSub = new imagenesSubasta(id_phone, informacion, contenType, fileName);

            em.persist(imagenSub);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    /**
     * Borra todas las imágenes asociadas a un teléfono
     * @param phone_id id del movil a las que las imagenes a borrar se encuentran asociadas
     * @return {@code true} Si se han podido borrar bien todas las imagenes asociadas a ese movil, {@code false} en otro caso
     */
    @Override
    public boolean borraTodas(Integer phone_id) {

        try {

            // Obtenemos el objeto asociado a dicha imagen

            String plantillaSQL = "select i from imagenesSubasta i where i.id_phone=:phone_id";
            TypedQuery<imagenesSubasta> query = em.createQuery(plantillaSQL, imagenesSubasta.class)
                    .setParameter("phone_id", phone_id);
            List<imagenesSubasta> imagenes = query.getResultList();

            for(imagenesSubasta imagen : imagenes) {

                // Borramos la entrada en la bbdd
                em.remove(imagen);

                // Borramos el archivo
                File archivo = new File(imagen.getImage_path());
                if( archivo.exists() ) {
                    if( archivo.delete() ) {
                        logger.log(Level.FINE, "Se ha borrado correctamente la imagen " + archivo.getName());
                    } else {
                        logger.log(Level.SEVERE, "No existe la imagen " + archivo.getName());
                    }
                }
            }

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }


}

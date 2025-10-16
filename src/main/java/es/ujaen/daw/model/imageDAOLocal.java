package es.ujaen.daw.model;

import es.ujaen.daw.enity.movil;
import es.ujaen.daw.imagenesSubasta;

import es.ujaen.daw.qualifiers.imageLocal;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de gestionar las imágenes de las subastas
 * Las almacena en una carpeta local dentro del home del usuario
 */
@Transactional
@RequestScoped
@imageLocal
public class imageDAOLocal implements imageDAO {

    private final String CARPETA_DESTINO = System.getProperty("user.home") + File.separator + "MobileMarket_imagenes";
    @PersistenceContext
    private EntityManager em;
    private final Logger logger = Logger.getLogger(imageDAOLocal.class.getName());


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

            for(imagenesSubasta imagen: imagenes) {

                Path fileRuta = Paths.get(imagen.getImage_path());
                byte[] imageInfo = Files.readAllBytes(fileRuta);

                StreamedContent contenido = DefaultStreamedContent.builder()
                        .contentType( imagen.getContentType() )
                        .stream(() -> new ByteArrayInputStream(imageInfo))
                        .build();
                contents.add(contenido);
            }

            return contents;

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }

    }

    /**
     * Dada una imagen, almacenalá dentro de la carpeta de imagenes y guarda la ruta dentro de la bbdd
     * @param imagen Evento que contiene todas las imágenes
     * @param phone Movil al que se le va a asociar la imagen
     * @return {@code true} Si se ha podido guardar la imagen correctamente, {@code false} en otro caso
     */
    @Override
    public boolean crea(FileUploadEvent imagen, movil phone) {

        try {
            int id_phone = phone.getId();

            // Eliminamos los espacios " " por "_"
            String nombreArchivoOriginal = imagen.getFile().getFileName();
            StringBuilder nombreImagen = new StringBuilder();
            nombreArchivoOriginal = nombreArchivoOriginal.replace(" ", "_");
            nombreImagen.append(phone.getMarca()).append(phone.getModelo()).append(nombreArchivoOriginal);

            // Creamos la carpeta en la guardas las imagenes
            File carpetaDestino = new File(CARPETA_DESTINO);
            if (!carpetaDestino.exists()) {
                if( !carpetaDestino.mkdirs() ) {
                    throw new FileNotFoundException("No se puede crear la carpeta");
                }
            }

            String ruta = CARPETA_DESTINO + File.separator + nombreImagen;

            // Creamos la imagen
            UploadedFile imagenFile = imagen.getFile();
            imagenesSubasta image = new imagenesSubasta(id_phone, ruta, imagenFile.getContentType(), imagenFile.getFileName());

            // Guardamos la imagen en la carpeta de destino
            InputStream inputStream = imagen.getFile().getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(CARPETA_DESTINO, nombreImagen.toString()));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

            // Guardamos la imagen en la bbdd
            em.persist(image);
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

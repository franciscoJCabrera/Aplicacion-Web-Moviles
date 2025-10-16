
package es.ujaen.daw;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Clase que representa las imagenes de una subasta
 */
@Entity
public class imagenesSubasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Identificador único de la fila dentro de la tabla
    private int id_phone; // Id del movil que representa la imagen
    private String image_path; // Path de la imagen de dicho movil

    private byte[] imagen; // Bytes que contiene la propia imagen
    private String contentType; // Tipo de la imagen guardada
    private String fileName; // Nombre de la imagen

    /**
     * Constructor usado para el almacenaje dentro de la base de datos
     * @param id_phone Id de la subasta a la que corresponde esta imagen
     * @param imagen bytes de la imagen que se tienen que almacenar
     * @param contentType Tipo de contenido (extension) de la imagen
     * @param fileName Nombre de la imagen original
     */
    public imagenesSubasta(int id_phone, byte[] imagen, String contentType, String fileName) {
        this.id_phone = id_phone;
        this.imagen = imagen;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    /**
     * Constructor para guardar la imagen en local con las cosas necesarias para ello
     * @param id_phone id de la subasta a la que pertenece esta imagen
     * @param image_path ruta en la que la imagen se ha guardado
     * @param contentType tipo de contenido de la imagen
     * @param fileName nombre de la imagen
     */
    public imagenesSubasta(int id_phone, String image_path, String contentType, String fileName) {
        this.id_phone = id_phone;
        this.image_path = image_path;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public imagenesSubasta() {

    }

    public int getId_phone() {
        return id_phone;
    }

    public void setId_phone(int id_phone) {
        this.id_phone = id_phone;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }
}

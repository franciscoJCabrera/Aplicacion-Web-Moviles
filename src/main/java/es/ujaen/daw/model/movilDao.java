package es.ujaen.daw.model;

import es.ujaen.daw.enity.movil;

import java.util.List;

public interface movilDao extends generalDAO<movil, Integer> {

    /**
     * Busca dentro de la bbdd al movil que tenga los datos de phone
     * @param phone Movil del que sacar los datos para buscar
     * @return Lista de móviles que tengan los valores pedidos
     */
    public List<movil> buscarMovil(movil phone);

    /**
     * Obten una lista de subastas de móviles destacados
     * @return Lista con subastas de móviles destacados
     */
    public List<movil> getListaDestacados();

    /**
     * Dado un usuario, devuelve las subastas
     * @return Lista con todas las subastas que ha hecho un usuario
     */
    public List<movil> getSubastasUsuario();
    /**
     * Dado un usuario, devuelve los moviles que ha tenido dicho usuario
     * @return Lista con todas las subastas que ha hecho un usuario
     */
    public List<movil> getMovilesUsuario();

    /**
     * Lista de todas las marcas de moviles que hay
     * @return Lista que contenga todas las marcas de moviles disponibles
     */
    public List<String> getListaMarcas();

    /**
     * Dada una marca, devuelve la lista de modelos que tiene asociada esa marca
     * @param Marca Marca de telefonos a los que buscar los modelos
     * @return Lista de modelos de dicha marca, si no tiene ninguno, la lista estará vacia
     */
    public List<String> getListaModelo(String Marca);

    /**
     * Obtén una lista con todas las capacidades de cargas
     * @return Lista con todas las capacidades de cargas de los teléfonos
     */
    public List<String> getListaCargas();

    /**
     * Obtén una lista con todas las tecnologías de pantallas disponibles
     * @return Lista con todas las tecnologías de pantallas de los teléfonos
     */
    public List<String> getListaPantallas();

    /**
     * Obtén una lista con todas las tasas de refrescos disponibles
     * @return Lista con todas las tasas de refrescos de los teléfonos
     */
    public List<String> getListaRefresco();

    /**
     * Obtén una lista con todas las capacidades de almacenamiento
     * @return Lista con todas las capacidades de almacenamiento de los teléfonos
     */
    public List<String> getListaCapacidadesAlmacenamiento();

    /**
     * Obtén una lista con todas las capacidades de RAM disponibles
     * @return Lista con todas las capacidades de RAM de los teléfonos
     */
    public List<String> getListaCapacidadRAM();

    /**
     * Dado la id de un movil, di si este movil existe dentro de la bbdd o no
     * @param id Id del movil a buscar
     * @return {@code true} si existe un movil con dicha id, {@code false} en otro caso
     */
    public boolean exists(Integer id);

    /**
     * Guarda a un usuario un nuevo movil como de su propiedad
     *
     * @param phone Movil que el usuario ha comprado
     * @param correoUsuario Correo (id) del usuario que ha comprado el movil
     * @return {@code true} Si se ha podido guardar el movil correctamente, {@code false} En otro caso
     */
    public boolean guardaMovilUsuario(movil phone, String correoUsuario);

    /**
     * Borra todas las subastas pertenecientes a un usuario
     */
    public void borraSubastasUsuario();
}

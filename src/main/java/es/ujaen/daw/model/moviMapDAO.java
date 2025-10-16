package es.ujaen.daw.model;
import es.ujaen.daw.enity.movil;
import jakarta.enterprise.context.ApplicationScoped;
import es.ujaen.daw.qualifiers.movilMap;

import java.io.Serializable;
import java.util.*;

@ApplicationScoped
@movilMap
public class moviMapDAO implements Serializable, movilDao {
    /**
     * Mapa donde se guardan todos los moviles, se cambiara por una bbdd en el futuro
     */
    private Map<Integer, movil> moviles;
    private Integer idMovil = 1;

    /**
     * Constructor por defecto
     * Crea unos moviles de ejemplo para ayudar en la visualizacion
     */
    public moviMapDAO() {

        moviles = new HashMap<>();

        List<String> moviles_imagenes = new ArrayList<>(2);
        
        // Metemos datos de prueba
        moviles.put(idMovil, new movil(idMovil++, "Samsung", "S24", 5500, 25, "Amoled", 120, 256, 8, 12, moviles_imagenes, 800));
        moviles.put(idMovil, new movil(idMovil++, "Samsung", "A54", 5000, 15, "Oled", 75, 128, 6, 8, moviles_imagenes, 450));

        moviles.put(idMovil, new movil(idMovil++, "Xiaomi", "S9", 4000, 40, "IPS", 60, 64, 4, 4, moviles_imagenes, 300));
        moviles.put(idMovil, new movil(idMovil++, "Iphone", "15 Pro", 5200, 17, "LCD", 80, 160, 18, 10, moviles_imagenes, 20000));


    }

    /**
     * Busca si existe un movil con una id ya dado
     * @param id Id que buscar dentro de la base de datos
     * @return True si existe un movil con esa id, false en otro caso
     */
    public boolean buscaID(int id) {
        return moviles.containsKey(id);
    }

    /**
     * Dada la id de un movil, devuelve el movil al que se refiere dentro de la BBDD
     * @param id Id del movil a buscar
     * @return Si existe el movil, devolvemos el movil, null en otro caso
     */
    @Override
    public movil buscaId(Integer id) {
        return moviles.get(id);
    }

    /**
     * Da una lista de todos los moviles que existen dentro de la BBDD
     * @return Una lista que contiene todos los moviles que existen dentro de la BBDD
     */
    @Override
    public List<movil> buscaTodos() {
        return new ArrayList<>(moviles.values());
    }

    /**
     * Dado un movil, añade dicho movil dentro de la BBDD, asegurandose de que el id sea unico
     * @param tel Movil que se va a añadir a la bbdd
     * @return True si se ha podido añadir el movil, falso en otro caso
     */
    @Override
    public boolean crea(movil tel) {

        movil nuevoTel = new movil(tel);
        nuevoTel.setId(idMovil);
        moviles.put(idMovil, nuevoTel);
        tel.setId(idMovil);
        idMovil++;

        return true;
    }

    /**
     * Busca dentro de la bbdd al movil que tenga los datos de phone
     * @param phone Movil del que sacar los datos para buscar
     * @return Lista de móviles que tengan los valores pedidos
     */
    @Override
    public List<movil> buscarMovil(movil phone) {

        return buscaTodos();
    }

    /**
     * Guarda los cambios realizados a tel dentro de la BBDD, solo si tel es un telefono que existe dentro de la BBDD
     * @param tel Telefono que tiene los cambios a guardar
     * @return True si se han podido guardar los cambios(tel existe en la BBDD), falso en caso de que tel no se encuentre dentro de la BBDD
     */
    @Override
    public boolean guarda(movil tel) {

        boolean resultado = false;
        if (moviles.containsKey(tel.getId())) {


            movil newTel = new movil(tel);
            moviles.replace(tel.getId(), newTel);
            resultado = true;
        }
        return resultado;
    }

    /**
     * Borra el telefono con id "id" dentro de la BBDD si un movil con dicha id existe
     * @param id ID del telefono que borrar
     * @return True si se ha encontrado el id en la BBDD y se ha borrado el telefono con dicha id, falso en otro caso
     */
    @Override
    public boolean borra(Integer id) {

        boolean resultado = false;
        if (moviles.containsKey(id)) {

            moviles.remove(id);
            resultado = true;
        }
        return resultado;
    }


    /**
     * Obten una lista de subastas de móviles destacados
     * @return Lista con subastas de móviles destacados
     */
    @Override
    public List<movil> getListaDestacados() {

        List<movil> destacados = new LinkedList<>();

        // Metemos moviles al azar
        try {
            destacados.addAll( moviles.values() );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return destacados;
    }

    /**
     * Dado un usuario, devuelve los moviles de dicho usuario
     * Al no contar aún con la BBDD devolvemos todos los moviles, cuando tengamos el usuario, devolveremos solo el del usuario
     * @return Lista con todas las subastas que ha hecho un usuario
     */
    @Override
    public List<movil> getSubastasUsuario() {
        return buscaTodos();
    }

    @Override
    public List<movil> getMovilesUsuario() {
        return null;
    }

    /**
     * Obten la lista de marcas que se encuentran en la BBDD
     * @return Lista de todas las marcas distintas de moviles que se encuentan en la BBDD
     */
    @Override
    public List<String> getListaMarcas() {
        return new ArrayList<>(List.of(new String[]{"Samsung", "Xiaomi"}));
    }

    /**
     * Dada una marca, devuelve la lista de modelos que tiene asociada esa marca
     * @param Marca Marca de telefonos a los que buscar los modelos
     * @return Lista de modelos de dicha marca, si no tiene ninguno, la lista estará vacia
     */
    @Override
    public List<String> getListaModelo(String Marca) {

        if( Marca.equals("Samsung") ) {

            return new ArrayList<>(List.of(new String[]{"A54", "S24"}));
        } else if ( Marca.equals("Xiaomi")) {

            return new ArrayList<>(List.of(new String[]{"S9"}));
        } else {

            return new ArrayList<>();
        }

    }

    /**
     * Obtén una lista con todas las capacidades de cargas
     * @return Lista con todas las capacidades de cargas de los teléfonos
     */
    @Override
    public List<String> getListaCargas() {
        return new ArrayList<>(List.of(new String[]{"20W", "40W"}));
    }

    /**
     * Obtén una lista con todas las tecnologías de pantallas disponibles
     * @return Lista con todas las tecnologías de pantallas de los teléfonos
     */
    @Override
    public List<String> getListaPantallas() {
        return new ArrayList<>(List.of(new String[]{"LCD", "IPS", "OLED", "AMOLED"}));
    }

    /**
     * Obtén una lista con todas las tasas de refrescos disponibles
     * @return Lista con todas las tasas de refrescos de los teléfonos
     */
    @Override
    public List<String> getListaRefresco() {
        return new ArrayList<>(List.of(new String[]{"60Hz", "90Hz", "120Hz"}));
    }

    /**
     * Obtén una lista con todas las capacidades de almacenamiento
     * @return Lista con todas las capacidades de almacenamiento de los teléfonos
     */
    @Override
    public List<String> getListaCapacidadesAlmacenamiento() {
        return new ArrayList<>(List.of(new String[]{"64", "128", "192", "256"}));
    }

    /**
     * Obtén una lista con todas las capacidades de RAM disponibles
     * @return Lista con todas las capacidades de RAM de los teléfonos
     */
    @Override
    public List<String> getListaCapacidadRAM() {
        return new ArrayList<>(List.of(new String[]{"6", "10", "12"}));
    }

    /**
     * Dado la id de un movil, di si este movil existe dentro de la bbdd o no
     * @param id Id del movil a buscar
     * @return {@code true} si existe un movil con dicha id, {@code false} en otro caso
     */
    @Override
    public boolean exists(Integer id) {
        return moviles.containsKey(id);
    }

    @Override
    public boolean guardaMovilUsuario(movil phone, String correoUsuario) {
        return false;
    }

    @Override
    public void borraSubastasUsuario() {

    }
}

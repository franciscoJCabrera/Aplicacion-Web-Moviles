package es.ujaen.daw.model;
import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import es.ujaen.daw.qualifiers.movilJPA;
import es.ujaen.daw.enity.movil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@movilJPA
@Transactional
@RequestScoped
public class movilJpaDao implements movilDao {

    @PersistenceContext
    private EntityManager em;

    @Inject @UsuarioJPA
    private UsuarioDao usuarioDao;

    private final Logger logger = Logger.getLogger(movilJpaDao.class.getName());


    /**
     * Busca un movil cuya id sea "id" y devuelvela si existe
     * @param id id del movil a buscar dentro de la base de datos
     * @return movil cuya id sea la pasada por los parámetros
     */
    @Override
    public movil buscaId(Integer id) {

        return em.find(movil.class, id);

    }

    /**
     * Devuelve todos los moviles guardados en la bbdd
     * @return Lista que contiene todas las subastas almacenadas en la bbdd
     */
    @Override
    public List<movil> buscaTodos() {
        try {
            return em.createQuery("select m from movil m", movil.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Crea una nueva subasta dentro de la base de datos
     * @param tel Subasta a guardar
     * @return {@code true} si se ha podido guardar correctamente, {@code false] en otro caso
     */
    @Override
    public boolean crea(movil tel) {
        try {

            // Guardamos la subasta con el correo del usuario con la sesion iniciada
            try {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                tel.setCorreoUsuario(request.getRemoteUser());
            } catch (Exception e) {
                logger.log(Level.INFO, "Metiendo un movil a traves de la API:\n" + tel.toString());
            }
            em.persist(tel);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Si la subasta ya existe dentro de la base de datos, actualiza los datos de la misma
     * para corresponderse con los de "tel", si no existe la subasta, se crea y almacena en la bbdd
     * @param tel Subasta que guardar o crear
     * @return {@code true} si se ha podido guardar o crear correctamente, {@code false en otro caso
     */
    @Override
    public boolean guarda(movil tel) {
        try {
            em.merge( tel );
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Borra la subasta con id "id" almacenada dentro de la bbdd
     * @param id Id de la subasta a borrar
     * @return {@code true} Si se ha borrado la subasta correctamente, {@code false} si no existe una subasta almacenada con esa id en la bbdd
     */
    @Override
    public boolean borra(Integer id) {

        try {
            movil sub = em.find(movil.class, id);
            em.remove(sub);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Dada una lista de resultados de busqueda y los parametros de la misma busqueda, elimina aquellos resultados que no cumplan los parametros
     * @param resultados Lista que contiene las listas buscadas
     * @param phone Movil con los parametros de busqueda que filtrar
     * @return Lista de resultados cuyos resultados cumplen todos los filtros de busqueda
     */
    private List<movil> filtraResultados( List<movil> resultados, movil phone ) {

        List<movil> resultados_filtrados = new ArrayList<>();
        for (movil phoneToFilter : resultados) {

            boolean cumple_filtros = true;
            if( phone.getMarca() != null ) {
                if( !phoneToFilter.getMarca().equals(phone.getMarca()) ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getModelo() != null && cumple_filtros) {
                if( !phoneToFilter.getModelo().equals(phone.getModelo()) ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getCapacidadBateria() > 0 && cumple_filtros) {
                if( phoneToFilter.getCapacidadBateria() < phone.getCapacidadBateria() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getVelocidadCarga() > 0 && cumple_filtros) {
                if( phoneToFilter.getVelocidadCarga() < phone.getVelocidadCarga() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getTecnologiaPantalla() != null && cumple_filtros) {
                if( !phoneToFilter.getTecnologiaPantalla().equals(phone.getTecnologiaPantalla()) ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getTasaRefresco() > 0 && cumple_filtros) {
                if( phoneToFilter.getTasaRefresco() < phone.getTasaRefresco() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getCapacidadAlmacenamiento() > 0 && cumple_filtros) {
                if( phoneToFilter.getCapacidadAlmacenamiento() < phone.getCapacidadAlmacenamiento() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getNumProcesadores() > 0 && cumple_filtros) {
                if( phoneToFilter.getNumProcesadores() < phone.getNumProcesadores() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getCapacidadRAM() > 0 && cumple_filtros) {
                if( phoneToFilter.getCapacidadRAM() < phone.getCapacidadRAM() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getPrecioCompra() > 0 && cumple_filtros) {
                if( phoneToFilter.getPrecioCompra() > phone.getPrecioCompra() ) {
                    
                    cumple_filtros = false;
                }
            }
            if( phone.getPrecioActual() > 0 && cumple_filtros) {
                if( phoneToFilter.getPrecioActual() > phone.getPrecioActual() ) {
                    cumple_filtros = false;
                }
            }
            // Tienen que ser subastas
            if(!phoneToFilter.isSubasta()) {
                cumple_filtros = false;
            }

            // Si cumple los filtros, lo metemos en la lista de resultados filtrados
            if( cumple_filtros ) {
                resultados_filtrados.add(phoneToFilter);
            }
        }
        return resultados_filtrados;
    }

    /**
     * Busca dentro de la bbdd al movil que tenga los datos de phone
     * @param phone Movil del que sacar los datos para buscar
     * @return Lista de móviles que tengan los valores pedidos
     */
    @Override
    public List<movil> buscarMovil(movil phone) {

        // Obtenemos todas las subastas con todos los parámetros y eliminamos las copias
        List<movil> resultados = new ArrayList<>();

        // Si no hay filtros (esta igual que una subasta nueva), devolvemos todos
        movil comp = new movil();
        if( phone.getId() == comp.getId() &&
                Objects.equals(phone.getCorreoUsuario(), comp.getCorreoUsuario()) &&
                phone.getCapacidadBateria() == comp.getCapacidadBateria() &&
                phone.getVelocidadCarga() == comp.getVelocidadCarga() &&
                phone.getTasaRefresco() == comp.getTasaRefresco() &&
                phone.getCapacidadAlmacenamiento() == comp.getCapacidadAlmacenamiento() &&
                phone.getNumProcesadores() == comp.getNumProcesadores() &&
                phone.getCapacidadRAM() == comp.getCapacidadRAM() &&
                phone.getPrecioCompra() == comp.getPrecioCompra() &&
                phone.getPrecioActual() == comp.getPrecioActual() &&
                phone.getNumVisitas() == comp.getNumVisitas() &&
                phone.getMarca() == null &&
                phone.getModelo() == null &&
                phone.getTecnologiaPantalla() == null
        ) {
            resultados = buscaTodos();
            List<movil> resultadosFiltrados = new ArrayList<>();
            // Eliminamos los que no sean subastas
            for(movil resultado : resultados ) {
                if( resultado.isSubasta() ) {
                    resultadosFiltrados.add(resultado);
                }
            }
            return resultadosFiltrados;
        }

        try {

            if( phone.getMarca() != null ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.marca=:marca";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("marca", phone.getMarca());
                resultados.addAll(query.getResultList());
            }
            if( phone.getModelo() != null ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.modelo=:modelo";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("modelo", phone.getModelo());
                resultados.addAll(query.getResultList());
            }
            if( phone.getCorreoUsuario() != null ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.correoUsuario=:correo";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("correo", phone.getCorreoUsuario());
                resultados.addAll(query.getResultList());
            }
            if( phone.getCapacidadBateria() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.capacidadBateria>=:bateria";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("bateria", phone.getCapacidadBateria());
                resultados.addAll(query.getResultList());
            }
            if( phone.getVelocidadCarga() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.velocidadCarga>=:carga";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("carga", phone.getVelocidadCarga());
                resultados.addAll(query.getResultList());
            }
            if( phone.getTecnologiaPantalla() != null ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.tecnologiaPantalla=:pantalla";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("pantalla", phone.getTecnologiaPantalla());
                resultados.addAll(query.getResultList());
            }
            if( phone.getTasaRefresco() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.tasaRefresco>=:refresco";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("refresco", phone.getTasaRefresco());
                resultados.addAll(query.getResultList());
            }
            if( phone.getCapacidadAlmacenamiento() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.capacidadAlmacenamiento>=:almacenamiento";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("almacenamiento", phone.getCapacidadAlmacenamiento());
                resultados.addAll(query.getResultList());
            }
            if( phone.getNumProcesadores() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.numProcesadores>=:numcpus";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("numcpus", phone.getNumProcesadores());
                resultados.addAll(query.getResultList());
            }
            if( phone.getCapacidadRAM() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.capacidadRAM>=:ram";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("ram", phone.getCapacidadRAM());
                resultados.addAll(query.getResultList());
            }
            if( phone.getPrecioCompra() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.precioCompra<=:precioBuy";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("precioBuy", phone.getPrecioCompra());
                resultados.addAll(query.getResultList());
            }
            if( phone.getPrecioActual() > 0 ) {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.precioActual<=:precioSubasta";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("precioSubasta", phone.getPrecioActual());
                resultados.addAll(query.getResultList());
            }

            // Eliminamos las subastas que se hayan seleccionado varias veces, dejando solo una
            List<movil> listaSinDupes = new ArrayList<>();
            for(movil mov : resultados ) {
                if(!listaSinDupes.contains(mov)) {
                    listaSinDupes.add(mov);
                }
            }

            // Filtramos los resultados, dejando solo aquellos que cumplen todos los filtros
            return filtraResultados(listaSinDupes, phone);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obten una lista de subastas de móviles destacados
     * @return Lista con subastas de móviles destacados
     */
    @Override
    public List<movil> getListaDestacados() {
        try {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String correo = request.getRemoteUser();
            // Si tiene sesion iniciada
            if( correo != null ) {
                // return em.createQuery("SELECT m FROM movil m WHERE m.numVisitas >= 1 AND m.correoUsuario <> :correo AND m.isSubasta = true ORDER BY m.numVisitas DESC", movil.class).getResultList();
                String plantillaSQL = "SELECT m FROM movil m WHERE m.numVisitas >= 1 AND m.correoUsuario <> :correo AND m.isSubasta = true ORDER BY m.numVisitas DESC";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("correo", correo);
                return query.getResultList();
            }
            // Si no tiene sesion iniciada
            else {

                return em.createQuery("SELECT m FROM movil m WHERE m.numVisitas >= 1 AND m.isSubasta = true ORDER BY m.numVisitas DESC", movil.class).getResultList();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Dado un usuario, devuelve los moviles de dicho usuario
     * @return Lista con todas las subastas que ha hecho un usuario
     */
    @Override
    public List<movil> getSubastasUsuario() {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            Usuario user = usuarioDao.buscarUsuarioCorreo(request.getRemoteUser());
            if(user == null) {
                return new ArrayList<>();
            }

            if (Objects.equals(user.getGrupo(), "ADMINISTRADORES")) {
                return buscaTodos();
            } else {
                String plantillaSQL = "SELECT m FROM movil m WHERE m.correoUsuario = :correo AND m.isSubasta = true";
                TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                        .setParameter("correo", user.getCorreoElectronico());
                return query.getResultList();
            }

    }

    @Override
    public List<movil> getMovilesUsuario() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Usuario user = usuarioDao.buscarUsuarioCorreo(request.getRemoteUser());
        if(user == null) {
            return new ArrayList<>();
        }

        if (Objects.equals(user.getGrupo(), "ADMINISTRADORES")) {
            return buscaTodos();
        } else {
            String plantillaSQL = "SELECT m FROM movil m WHERE m.correoUsuario = :correo AND m.isSubasta = false";
            TypedQuery<movil> query = em.createQuery(plantillaSQL, movil.class)
                    .setParameter("correo", user.getCorreoElectronico());
            return query.getResultList();
        }
    }

    /**
     * Lista de todas las marcas de moviles que hay
     * @return Lista que contenga todas las marcas de moviles disponibles
     */
    @Override
    public List<String> getListaMarcas() {
        try {
            return em.createQuery("SELECT m.marca FROM movil m GROUP BY m.marca", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Dada una marca, devuelve la lista de modelos que tiene asociada esa marca
     * @param Marca Marca de telefonos a los que buscar los modelos
     * @return Lista de modelos de dicha marca, si no tiene ninguno, la lista estará vacia
     */
    @Override
    public List<String> getListaModelo(String Marca) {
        try {

            String plantillaSQL = "SELECT m.modelo FROM movil m WHERE m.marca=:marca";
            TypedQuery<String> query = em.createQuery(plantillaSQL, String.class)
                    .setParameter("marca", Marca);
            return query.getResultList();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtén una lista con todas las capacidades de cargas
     * @return Lista con todas las capacidades de cargas de los teléfonos
     */
    @Override
    public List<String> getListaCargas() {
        try {
            return em.createQuery("SELECT m.velocidadCarga FROM movil m GROUP BY m.velocidadCarga", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtén una lista con todas las tecnologías de pantallas disponibles
     * @return Lista con todas las tecnologías de pantallas de los teléfonos
     */
    @Override
    public List<String> getListaPantallas() {
        try {
            return em.createQuery("SELECT m.tecnologiaPantalla FROM movil m GROUP BY m.tecnologiaPantalla", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtén una lista con todas las tasas de refrescos disponibles
     * @return Lista con todas las tasas de refrescos de los teléfonos
     */
    @Override
    public List<String> getListaRefresco() {
        try {
            return em.createQuery("SELECT m.tasaRefresco FROM movil m GROUP BY m.tasaRefresco", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtén una lista con todas las capacidades de almacenamiento
     * @return Lista con todas las capacidades de almacenamiento de los teléfonos
     */
    @Override
    public List<String> getListaCapacidadesAlmacenamiento() {
        try {
            return em.createQuery("SELECT m.capacidadAlmacenamiento FROM movil m GROUP BY m.capacidadAlmacenamiento", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtén una lista con todas las capacidades de RAM disponibles
     * @return Lista con todas las capacidades de RAM de los teléfonos
     */
    @Override
    public List<String> getListaCapacidadRAM() {
        try {
            return em.createQuery("SELECT m.capacidadRAM FROM movil m GROUP BY m.capacidadRAM", String.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Dado la id de un movil, di si este movil existe dentro de la bbdd o no
     * @param id Id del movil a buscar
     * @return {@code true} si existe un movil con dicha id, {@code false} en otro caso
     */
    @Override
    public boolean exists(Integer id) {
        return em.find(movil.class, id) != null;
    }

    /**
     * Guarda a un usuario un nuevo movil como de su propiedad
     *
     * @param phone Movil que el usuario ha comprado
     * @param correoUsuario Correo (id) del usuario que ha comprado el movil
     * @return {@code true} Si se ha podido guardar el movil correctamente, {@code false} En otro caso
     */
    @Override
    public boolean guardaMovilUsuario(movil phone, String correoUsuario) {

        try {
            phone.setCorreoUsuario(correoUsuario);
            em.merge(phone);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void borraSubastasUsuario() {
        List<movil> resultados = getSubastasUsuario();
        for(movil resultado : resultados) {
            borra(resultado.getId());
        }
    }
}

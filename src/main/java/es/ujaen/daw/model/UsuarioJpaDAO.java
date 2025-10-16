package es.ujaen.daw.model;

import es.ujaen.daw.AuthService;
import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@UsuarioJPA
@Transactional
@RequestScoped
public class UsuarioJpaDAO implements UsuarioDao {

    ///Declaramos el EntityManager
    @PersistenceContext
    private EntityManager em;

    private final Logger logger = Logger.getLogger(UsuarioJpaDAO.class.getName());

    @Inject
    private AuthService authService;


    @Override
    public List<String> devolverPaises() {
        return new ArrayList<>(List.of(new String[]{"España","Francia","Portugal","Alemania"}));
    }

    @Override
    public List<String> devolverCiudades(String pais) {
        if (pais.equals("España")){
            return new ArrayList<>(List.of(new String[]{"Jaen","Cordoba","Sevilla","Cadiz"}));
        } else if (pais.equals("Francia")){
            return new ArrayList<>(List.of(new String[]{"Marsella","Paris","Lyon","Niza"}));
        } else if (pais.equals("Portugal")){
            return new ArrayList<>(List.of(new String[]{"Lisboa","Oporto","Braga","Aveiro"}));
        } else if (pais.equals("Alemania")){
            return new ArrayList<>(List.of(new String[]{"Berlin","Munich","Hamburgo","Dresde"}));
        }

        return new ArrayList<>();
    }

    /**
     * Metodo para buscar un usuario dado un correo electronico (identificador del usuario, es unico para cada uno)
     * @param correoElectronico
     * @return
     */
    @Override
    public Usuario buscarUsuarioCorreo(String correoElectronico) {
        String plantillaSQL = "SELECT u FROM Usuario u WHERE u.correoElectronico=:correo";
        TypedQuery<Usuario> query = em.createQuery(plantillaSQL, Usuario.class)
                .setParameter("correo", correoElectronico);
        return query.getSingleResult();
    }

    @Override
    public Usuario buscaId(String id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> buscaTodos() {
        try{
            return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
        } catch (Exception exception){
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            return null;
        }
    }

    @Override
    public boolean crea(Usuario tel) {
        try{
            tel.setContraCifrada(authService.encryptPassword(tel.getContrasenia()));
            em.persist(tel);
            return true;
        } catch (Exception exception){
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            return false;
        }
    }

    @Override
    public boolean guarda(Usuario tel) {
        try{
            em.merge(tel);
            return true;
        } catch (Exception exception){
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            return false;
        }
    }

    @Override
    public boolean borra(String id) {
        try {
            Usuario usuario = em.find(Usuario.class, id);
            em.remove(usuario);
            return true;
        } catch (Exception exception){
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            return false;
        }
    }
}

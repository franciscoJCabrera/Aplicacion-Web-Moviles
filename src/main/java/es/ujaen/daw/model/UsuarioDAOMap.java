package es.ujaen.daw.model;

import es.ujaen.daw.enity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UsuarioDAOMap implements Serializable, UsuarioDao {

    private Map<String, Usuario> usuarios;
    private String dni = "26292629X";

    ///Definimos el constructor
    public UsuarioDAOMap(){
        this.usuarios = new HashMap<>();
    }

    @Override
    public Usuario buscaId(String id) {
        return usuarios.get(dni);
    }

    @Override
    public List<Usuario> buscaTodos() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public boolean crea(Usuario newUser) {
        Usuario usuarioNuevo = new Usuario(newUser);
        usuarios.put(newUser.getDni(),newUser);
        return true;
    }

    @Override
    public boolean guarda(Usuario newUser) {
        boolean resultado = false;
        if (usuarios.containsKey(newUser.getDni())) {
            Usuario usuario = new Usuario();
            usuarios.replace(newUser.getDni(), newUser);
            resultado = true;
        }
        return resultado;
    }

    @Override
    public boolean borra(String dniBuscar) {
        boolean resultado = false;
        if (usuarios.containsKey(dniBuscar)) {
            usuarios.remove(dniBuscar);
            resultado = true;
        }
        return resultado;
    }

    /**
     * Metodo que te muestra un listado de paises a elegir
     * @return
     */
    public List<String> devolverPaises(){
        return new ArrayList<>(List.of(new String[]{"España","Francia","Portugal","Alemania"}));
    }

    /**
     * Metodo que tras seleccionar un pais te devuelve un listado u otro de diferentes ciudades
     * @param pais
     * @return
     */
    public List<String> devolverCiudades(String pais){
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
     * Metodo el cual nos busca un usuario dado un correo electronico (identificador, es unico para cada usuario)
     * @param correoElectronico
     * @return
     */
    @Override
    public Usuario buscarUsuarioCorreo(String correoElectronico) {
        return null;
    }

}

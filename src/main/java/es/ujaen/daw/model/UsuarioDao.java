package es.ujaen.daw.model;

import es.ujaen.daw.enity.Usuario;

import java.util.List;

public interface UsuarioDao extends generalDAO<Usuario, String> {

    /**
     * Metodo con el cual se muestra un listado de Paises entre los cuales va a poder seleccionar el usuario
     */
    public List<String> devolverPaises();

    /**
     * Metodo con el cual se obtiene una serie de ciudades dependiendo del Pais seleccionado por el usuario
     */
    public List<String> devolverCiudades(String pais);

    /**
     * Metodo con el cual se busca un usuario dado un correo electronico
     * @param correoElectronico
     * @return
     */
    public Usuario buscarUsuarioCorreo(String correoElectronico);
}

package es.ujaen.daw.enity;

import es.ujaen.daw.AuthService;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {

    ///Declaramos todas las variables que un usuario puede tener
    ///Los @Pattern son mensajes de error mostrados cuando se va a intentar introducir finalmente ese dato
    @Pattern(regexp = "[a-zA-Z ]{1,20}", message = "El nombre debe contener solamente letras, sin acentos, con una longitud máxima de 20 caracteres")
    private String nombre;
    @Pattern(regexp = "[a-zA-Z ]{1,20}", message = "Los apellidos del usuario debe contener solamente letras, con una longitud máxima de 20 caracteres")
    private String apellidos;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!.])(?!.*\\s).{8,20}$", message = "La contraseña debe contener al menos una letra minúscula, una letra mayúscula, un número y un símbolo, y tener una longitud entre 8 y 20 caracteres")
    transient private String contrasenia;   ///Si es transient no se guarda en la tabla de la base de datos h2

    private String contraCifrada;

    @Pattern(regexp = "\\d{8}[a-zA-Z]", message = "El DNI debe contener 8 números seguidos de una letra")
    private String dni;

    @Pattern(regexp = "^\\d{9}$", message = "El número de teléfono debe contener solamente números y tener exactamente 9 dígitos")
    private String numeroTelefono;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$", message = "El nombre de usuario debe tener entre 8 y 20 caracteres, y debe contener letras y números.")
    private String nombreUsuario;

    ///Vamos a tener como id el correo electronico ya que este va a ser unico
    @Id
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9]{5,}@(gmail\\.com|hotmail\\.com|hotmail\\.es|red\\.ujaen\\.es)$", message = "El correo electrónico debe tener una longitud mínima de 5 caracteres, conteniendo letras y números o solamente letras, y tener una de las siguientes terminaciones: @gmail.com, @hotmail.com, @hotmail.es o @red.ujaen.es")
    private String correoElectronico;
    private String direccion;
    private String pais;
    private String ciudad;

    @Pattern(regexp = "^\\d{5}$", message = "El código postal debe tener 5 dígitos.")
    private String codigoPostal;
    private String grupo;

    ///Definimos el constructor por defecto
    public Usuario(String nombre, String apellidos, String contrasenia, String dni, String numeroTelefono, String nombreUsuario, String correoElectronico, String direccion, String pais, String ciudad, String  codigoPostal, String imagenUsuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrasenia = contrasenia;
        this.dni = dni;
        this.numeroTelefono = numeroTelefono;
        this.nombreUsuario = nombreUsuario;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.grupo = "USUARIOS";
    }

    ///Definimos el constructor copia
    public Usuario(Usuario usuario){
        this.nombre = usuario.nombre;
        this.apellidos = usuario.apellidos;
        this.contrasenia = usuario.contrasenia;
        this.dni = usuario.dni;
        this.numeroTelefono = usuario.numeroTelefono;
        this.nombreUsuario = usuario.nombreUsuario;
        this.correoElectronico = usuario.correoElectronico;
        this.direccion = usuario.direccion;
        this.pais = usuario.pais;
        this.ciudad = usuario.ciudad;
        this.codigoPostal = usuario.codigoPostal;
        this.grupo = usuario.grupo;
    }

    public Usuario() {
        this.nombre = "Francisco Jose";
        this.apellidos = "Cabrera Bermejo";
        this.contrasenia = "Secret.1";
        this.dni = "26502929X";
        this.numeroTelefono = "603545454";
        this.nombreUsuario = "Franciscco22";
        this.correoElectronico = "admin@red.ujaen.es";
        this.direccion = "Calle malaga numero 22";
        this.pais = "España";
        this.ciudad = "Jaen";
        this.codigoPostal = "23003";
        this.grupo = "USUARIOS";
    }


    ///Hacemos los Gets
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getDni() {
        return dni;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String  getCodigoPostal() {
        return codigoPostal;
    }

    ///Hacemos los Sets
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setContrasenia(String contraseña) {
        this.contrasenia = contraseña;
    }

    public String getGrupo() {
        return grupo;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodigoPostal(String  codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getContraCifrada() {
        return contraCifrada;
    }

    public void setContraCifrada(String contraCifrada) {
        this.contraCifrada = contraCifrada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(correoElectronico, usuario.correoElectronico);
    }


}

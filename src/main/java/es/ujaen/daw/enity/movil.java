package es.ujaen.daw.enity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Entity
public class movil implements Serializable {


    transient private final String CARPETA_DESTINO = System.getProperty("user.home") + File.separator + "mobile_market_imagenes" + File.separator + "moviles";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // ID que tiene el telefono, necesario para almacenarlo en la BBDD

    private String correoUsuario; // Correo del usuario que ha creado la subasta

    private String marca; // Marca del movil

    private String modelo; // Modelo del movil
    @Min(value = 1000, message = "La capacidad de la batería debe de tener un mínimo de 4 números")
    private int capacidadBateria; // Capacidad de la bateria del movil (medida en Ma)

    @Min(value = 1, message = "La velocidad de carga debe de ser mayor a 0")
    private int velocidadCarga; // Velocidad de carga del telefono (se mide en W)
    @Pattern(regexp = ("Amoled|LCD|IPS|Oled"), message = "La tecnología de la pantalla debe de ser Amoled, Oled, LCD o IPS")
    private String tecnologiaPantalla; // Tipo de tecnologia que usa la pantalla del telefono
    @Min(value = 60, message = "La tasa de refresco de la pantalla tiene que ser mayor a 60")
    private int tasaRefresco; // Tasa de refresco de la pantalla (Se mide en Hz)
    @Min(value = 10, message = "La capacidad de almacenamiento debe de tener un mínimo de 2 dígitos")
    private int capacidadAlmacenamiento; // Capacidad (en GB) de memoria secundaria
    @Min(value = 1, message = "El número de procesadores tiene que ser mínimo 1")
    private int numProcesadores; // Número de unidades de procesamientos (nucleos de la CPU)
    @Min(value = 2, message = "La capacidad en RAM debe de ser, como mínimo, de 2GB")
    private int capacidadRAM; // Capacidad (en GB) de la memoria RAM

    @Min(value = 1, message = "El precio pedido por el dispositivo tiene que ser mayor a 0")
    private int precioCompra; // Precio por el que se puede comprar directamente el telefono
    @Column(name = "precioSubasta")
    private int precioActual; // Precio actual del teléfono en la subasta
    private int numVisitas; // Numero de visitas que ha tenido esta subasta
    private boolean isSubasta; // Determina si el movil es una subasta o si ya ha sido comprado

    public movil(int id, String marca, String modelo, int capacidadBateria, int velocidadCarga, String tecnologiaPantalla, int tasaRefresco, int capacidadAlmacenamiento, int numProcesadores, int capacidadRAM, List<String> images, int precioCompra) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadBateria = capacidadBateria;
        this.velocidadCarga = velocidadCarga;
        this.tecnologiaPantalla = tecnologiaPantalla;
        this.tasaRefresco = tasaRefresco;
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.numProcesadores = numProcesadores;
        this.capacidadRAM = capacidadRAM;
        this.precioCompra = precioCompra;
        this.precioActual = 0;
        this.numVisitas = 0;
        this.correoUsuario = "";
        this.isSubasta = true;
    }

    /**
     * Contructor copia
     * @param movil Telefono del que se quiere copiar los datos
     */
    public movil(movil movil) {
        this.id = movil.id;
        this.marca = movil.marca;
        this.modelo = movil.modelo;
        this.capacidadBateria = movil.capacidadBateria;
        this.velocidadCarga = movil.velocidadCarga;
        this.tecnologiaPantalla = movil.tecnologiaPantalla;
        this.tasaRefresco = movil.tasaRefresco;
        this.capacidadAlmacenamiento = movil.capacidadAlmacenamiento;
        this.numProcesadores = movil.numProcesadores;
        this.capacidadRAM = movil.capacidadRAM;
        this.precioCompra = movil.precioCompra;
        this.precioActual = movil.precioActual;
        this.numVisitas = movil.numVisitas;
        this.correoUsuario = movil.correoUsuario;
        this.isSubasta = movil.isSubasta;
    }


    public movil() {
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.tecnologiaPantalla = "";
        this.capacidadBateria = 0;
        this.velocidadCarga = 0;
        this.tasaRefresco = 0;
        this.capacidadAlmacenamiento = 0;
        this.numProcesadores = 0;
        this.capacidadRAM = 0;
        this.precioCompra = 0;
        this.precioActual = 0;
        this.numVisitas = 0;
        this.correoUsuario = "";
        this.isSubasta = true;
    }

    /**
     * Determina que el movil se ha comprado, así que ya no es una subasta
     */
    public void setComprado() {
        this.isSubasta = false;
    }

    public boolean isSubasta() {
        return isSubasta;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }


    /**
     * Añade una visita al contador de visitas
     */
    public void addVisita() {
        this.numVisitas++;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public int getNumVisitas() {
        return numVisitas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidadBateria() {
        return capacidadBateria;
    }

    public void setCapacidadBateria(int capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public int getVelocidadCarga() {
        return velocidadCarga;
    }

    public void setVelocidadCarga(int velocidadCarga) {
        this.velocidadCarga = velocidadCarga;
    }

    public String getTecnologiaPantalla() {
        return tecnologiaPantalla;
    }

    public void setTecnologiaPantalla(String tecnologiaPantalla) {
        this.tecnologiaPantalla = tecnologiaPantalla;
    }

    public int getTasaRefresco() {
        return tasaRefresco;
    }

    public void setTasaRefresco(int tasaRefresco) {
        this.tasaRefresco = tasaRefresco;
    }

    public int getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }

    public void setCapacidadAlmacenamiento(int capacidadAlmacenamiento) {
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
    }

    public int getNumProcesadores() {
        return numProcesadores;
    }

    public void setNumProcesadores(int numProcesadores) {
        this.numProcesadores = numProcesadores;
    }

    public int getCapacidadRAM() {
        return capacidadRAM;
    }

    public void setCapacidadRAM(int capacidadRAM) {
        this.capacidadRAM = capacidadRAM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public int getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(int precioActual) {
        this.precioActual = precioActual;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        movil other = (movil) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "movil{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", capacidadBateria=" + capacidadBateria +
                ", velocidadCarga=" + velocidadCarga +
                ", tecnologiaPantalla='" + tecnologiaPantalla + '\'' +
                ", tasaRefresco=" + tasaRefresco +
                ", capacidadAlmacenamiento=" + capacidadAlmacenamiento +
                ", numProcesadores=" + numProcesadores +
                ", capacidadRAM=" + capacidadRAM +
                ", precioCompra=" + precioCompra +
                ", precioActual=" + precioActual +
                ", numVisitas=" + numVisitas +
                '}';
    }


}

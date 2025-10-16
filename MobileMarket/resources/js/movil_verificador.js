let el = selector => document.querySelector(selector);

class movilVerifier {
    constructor() {
        this.srvUrl = "http://localhost:8080/MobileMarket/api/movil/formSubasta"; // URL del servicio REST
    }
    init() {

        el('#dialogs').addEventListener('submit', event => {
            this.verificar_movil(event);
        });
    }

    async verificar_movil(event) {
        event.preventDefault();

        let movil_correcto = true; // Flag que determina si el movil cumple o no las restricciones

        console.log("Se ha dado al boton de verificar datos")
        let marca = el('[name="dialogs:marca"]').value;
        let modelo = el('[name="dialogs:modelo"]').value;
        let bateria = this.limpia_pInputText( el('[name="dialogs:bateria_input"]').value);
        let carga = this.limpia_pInputText( el('[name="dialogs:carga_input"]').value );
        let pantalla = el('[name="dialogs:pantalla"]').value;
        let tasa_refresco =this.limpia_pInputText( el('[name="dialogs:refresto_input"]').value);
        let num_proc = this.limpia_pInputText( el('[name="dialogs:cpu_input"]').value );
        let almacenamiento = this.limpia_pInputText( el('[name="dialogs:almacenamiento_input"]').value );
        let ram = this.limpia_pInputText( el('[name="dialogs:ram_input"]').value );
        let precio_venta = this.limpia_pInputText( el('[name="dialogs:precio_input"]').value );
        let id = el('[name="dialogs:id_movil_input"]').value | 0;
        let correo = el('[name="dialogs:correo_subasta"]').value;

        // Comprobamos que la marca no este vacia
        if(marca.trim() === '') {
            this.dar_mensaje("error_marca", "La marca no puede estar vacia");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_marca");
        }

        // Comprobamos que el modelo no este vacio
        if(modelo.trim() === '') {
            this.dar_mensaje("error_modelo", "El modelo no puede estar vacio");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_modelo");
        }

        // Comprobamos que la bateria no este vacia
        if(bateria < 1000) {
            this.dar_mensaje("error_bateria", "La batería tiene que tener un mínimo de 4 dígitos");
            movil_correcto = false;
        }  else {
            this.eliminarMensaje("error_bateria");
        }

        // Comprobamos la velocidad de carga
        if(carga <= 0) {
            this.dar_mensaje("error_carga", "La velocidad de carga debe de ser mayor a 0");
            movil_correcto = false;
        }  else {
            this.eliminarMensaje("error_carga");
        }

        // Comprobamos la pantalla
        if( /Amoled|LCD|IPS|Oled/.test(pantalla) === false ) {
            this.dar_mensaje("error_pantalla", "La tecnología de la pantalla debe de ser Amoled, Oled, LCD o IPS");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_pantalla");
        }

        // Comprobamos la tasa de refresco
        if( tasa_refresco < 60 ) {
            this.dar_mensaje("error_tasa_refresco", "La tasa de refresco de la pantalla tiene que ser mayor o igual a 60");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_tasa_refresco");
        }

        // Comprobamos el numero de procesadores
        if( num_proc < 1 ) {
            this.dar_mensaje("error_num_proc", "El número de procesadores tiene que ser mínimo 1");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_num_proc");
        }

        // Comprobamos la capacidad de almacenamiento
        if( almacenamiento < 10 ) {
            this.dar_mensaje("error_almacenamiento", "La capacidad de almacenamiento debe de tener un mínimo de 2 dígitos");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_almacenamiento");
        }

        // Comprobamos la capacidad ram
        if( ram < 2 ) {
            this.dar_mensaje("error_ram", "La capacidad en RAM debe de ser, como mínimo, de 2GB");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_ram");
        }

        // Comprobamos el precio de subasta
        if(precio_venta <= 0) {
            this.dar_mensaje("error_precio", "El precio pedido por el dispositivo tiene que ser mayor a 0");
            movil_correcto = false;
        } else {
            this.eliminarMensaje("error_precio");
        }

        // Si se cumple la validacion, enviamos el formulario
        if(movil_correcto) {

            PF('manageProductDialog').hide();
            location.reload();

            let movil = {
                id: id,
                correoUsuario: correo,
                marca: marca,
                modelo: modelo,
                capacidadBateria: bateria,
                velocidadCarga: carga,
                tecnologiaPantalla: pantalla,
                tasaRefresco: tasa_refresco,
                numProcesadores: num_proc,
                capacidadAlmacenamiento: almacenamiento,
                capacidadRAM: ram,
                precioCompra: precio_venta,
                precioActual: 0,
                numVisitas: 0,
                isSubasta: true
            };
            await this.enviaSubasta(movil);

            console.log("Datos de movil correctos, envindo")
        }
    }

    async enviaSubasta(subasta) {
        let enviado = false;
        let errores= [];
        console.log(JSON.stringify(subasta));
        try {
            let response = await fetch(this.srvUrl, {
                method: 'POST',
                body: JSON.stringify(subasta),
                headers: {
                    'Content-type': 'application/json',
                    'accept': 'application/json'
                }
            })
            //get json response: libro or bean validation errors
            let data = await response.json();
            if (response.ok) { //libro accepted on server? (status 200)
                enviado = true;
                console.log(`Confirmada alta de subasta: ${data.marca} ${data.modelo}`);
            }
        } catch (ex) { //Network error
            errores = "Error en conexión";
            console.error(errores);
        }

        return enviado;
    }

    limpia_pInputText(valor) {
        if(valor === null || valor === undefined) {
            return 0;
        } else {
            valor = valor.replace(/\./g, '');
            return parseInt(valor);
        }
    }

    dar_mensaje(id_elemento, mensaje) {
        let nodo = document.getElementById(id_elemento);

        let nodo_error = document.createElement("p");
        nodo_error.className = "text-danger";
        nodo_error.textContent = mensaje;
        if(nodo.firstChild) {
            nodo.removeChild(nodo.firstChild);
        }
        nodo.appendChild(nodo_error);
    }

    eliminarMensaje(id_elemento) {
        let nodo = document.getElementById(id_elemento);
        while (nodo.firstChild) {
            nodo.removeChild(nodo.firstChild);
        }
    }
}

window.addEventListener('load', () => {
        window.controller = new movilVerifier();
        console.log('Inicializando el verificador de movil')
        controller.init();
    }
)
let el = selector => document.querySelector(selector);

class movilCtrl {
    constructor() {
        this.srvUrl = "http://localhost:8080/MobileMarket/api/movil"; // URL del servicio REST
        this.moviles = [];
    }
    init() {
        this.cargaSubastas();
        el('#alta_movil').addEventListener('submit', event => {
            this.alta(event);
        });
    }
    cargaSubastas() {
        return fetch( this.srvUrl )
            .then( response =>response.json() )
            .then( subastas => {
                this.subastas = subastas;
                this.visualizaSubasta();
                return true;
                }
            )
            .catch(() => {
                    el('#errores_validacion').innerHTML = "Error de conexion";
                    console.error("Error de conexion");
                    return false;
                }
            )
    }

    visualizaSubasta() {
        let ul = el('#lista_moviles')
        ul.innerHTML = '';
        this.subastas.forEach( subasta => {
            let li = document.createElement('li');
            li.innerHTML = `${subasta.marca} ${subasta.modelo}` ;
            ul.appendChild(li);
        } )
    }
    async alta(event) {
        event.preventDefault();
        let marca = el('[name="marca"]').value;
        let modelo = el('[name="modelo"]').value;
        let correo = el('[name="email"]').value;
        let bateria = parseInt( el('[name="capacidad_bateria"]').value | 0);
        let carga = parseInt( el('[name="capacidad_carga"]').value | 0 );
        let pantalla = el('[name="pantalla"]').value;
        let tasa_refresco =parseInt( el('[name="tasa_refresco"]').value |  0);
        let num_proc = parseInt( el('[name="num_procesadores"]').value | 0 );
        let almacenamiento = parseInt( el('[name="almacenamiento"]').value | 0 );
        let ram = parseInt( el('[name="ram"]').value | 0 );
        let precio_venta = parseInt( el('[name="precio_venta"]').value | 0 );

        let movil = {
            marca: marca,
            modelo: modelo,
            correoUsuario: correo,
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
        if (await this.enviaSubasta(movil)) {
            el('#alta_movil').reset();
            this.cargaSubastas();
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
            } else { // bean-validation errors!

                for (let i = 0; i < data.length; i++) {
                    errores.push(data[i].message);
                    console.warn(data[i]);
                }

            }
        } catch (ex) { //Network error
            errores = "Error en conexión";
            console.error(errores);
        }
        let erroresHtml = "";
        if(errores === "Error en conexión") {
            el('#errores_validacion').innerHTML = errores; //show or clear errors
        } else {
            for (let error of errores) {
                erroresHtml += "<p>" + error + "</p>";
            }
            el('#errores_validacion').innerHTML = erroresHtml; //show or clear errors
        }
        return enviado;
    }
}

window.addEventListener('load', () => {
    window.ctrl = new movilCtrl();
    console.log('Inicializando el controlador de movil')
    ctrl.init();
    }
)
let el = selector => document.querySelector(selector);

class usuarioControlador{

    ///Hacemos el constructor
    constructor() {
        this.srvUrl = "http://localhost:8080/MobileMarket/api/usuario";
        this.usuarios = [];
    }

    init() {
        this.cargaUsuarios();
        el('#altaUsuario').addEventListener('submit', event => {
            this.alta(event);
        });
    }
    cargaUsuarios() {
        return fetch( this.srvUrl )
            .then( response =>response.json() )
            .then( usuarios => {
                    this.usuarios = usuarios;
                    this.visualizaUsuarios();
                    return true;
                }
            )
            .catch(() => {
                    el('#errorValidacionUsuarios').innerHTML = "Error de conexion";
                    console.error("Error de conexion");
                    return false;
                }
            )
    }

    visualizaUsuarios() {
        let ul = el('#listadoUsuarios')
        ul.innerHTML = '';
        this.usuarios.forEach( usuario => {
            let li = document.createElement('li');
            li.innerHTML = `${usuario.nombre}` ;
            ul.appendChild(li);
        } )
    }

    async alta(event) {
        event.preventDefault();
        let nombre = el('[name="nombre"]').value;
        let apellidos = el('[name="apellidos"]').value;
        let contra = el('[name="contra"]').value;
        let dni =  el('[name="dni"]').value;
        let numeroTelefono = el('[name="telefono"]').value;
        let nickname = el('[name="nickname"]').value;
        let correoElectronico = el('[name="email"]').value;
        let direccion =  el('[name="direccion"]').value;

        ///Asignamos los valores
        let usuario = {
            nombre: nombre,
            apellidos: apellidos,
            contrasenia: contra,
            dni: dni,
            numeroTelefono: numeroTelefono,
            nombreUsuario: nickname,
            correoElectronico: correoElectronico,
            direccion: direccion,
        };
        if (await this.enviaUsuario(usuario)) {
            el('#altaUsuario').reset();
            this.cargaUsuarios();
        }
    }
    async enviaUsuario(usuario2) {
        let enviado = false;
        let errores= [];
        console.log(JSON.stringify(usuario2));
        try {
            let response = await fetch(this.srvUrl, {
                method: 'POST',
                body: JSON.stringify(usuario2),
                headers: {
                    'Content-type': 'application/json',
                    'accept': 'application/json'
                }
            })
            //get json response: libro or bean validation errors
            let data = await response.json();
            if (response.ok) { //libro accepted on server? (status 200)
                enviado = true;
                console.log(`Confirmada alta de usuario: ${data.nombre} ${data.apellidos}`);
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
            el('#errorValidacionUsuarios').innerHTML = errores; //show or clear errors
        } else {
            for (let error of errores) {
                erroresHtml += "<p>" + error + "</p>";
            }
            el('#errorValidacionUsuarios').innerHTML = erroresHtml; //show or clear errors
        }
        return enviado;
    }
}

window.addEventListener('load', () => {
        window.ctroller = new usuarioControlador();
        console.log('Inicializando el controlador de usuario')
        ctroller.init();
    }
)
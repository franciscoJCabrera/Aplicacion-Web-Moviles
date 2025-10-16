function pulsarBoton() {
    let tabla = document.getElementById("tabla")
    let nuevoP = document.createElement("p");
    nuevoP.innerHTML="Has pulsado un boton";
    nuevoP.className="text-secondary";
    nuevoP.id="newButton"
    let nodo = document.getElementById("newButton");
    if( nodo != null ) {

        nuevoP.innerHTML="Deja de pulsarme"
        tabla.removeChild(nodo);
        tabla.appendChild(nuevoP);

    } else {

        tabla.appendChild(nuevoP);
    }

}

function mouseEncimaBoton() {
    boton.className="btn btn-lg btn-secondary";
}
function mouseLeaveBoton() {
    boton.className="btn btn-lg btn-primary";
}

let boton = document.getElementById("boton");
boton.addEventListener('click', pulsarBoton);
boton.addEventListener("mouseover", mouseEncimaBoton);
boton.addEventListener("mouseleave", mouseLeaveBoton);
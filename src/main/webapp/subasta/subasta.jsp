<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<jsp:useBean id="movil" type="es.ujaen.daw.enity.movil" scope="request"/>
<jsp:useBean id="user" type="es.ujaen.daw.enity.Usuario" scope="request"/>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Mobile Market</title>
</head>
<body class="bg-dark-subtle">

<header class="p-3 text-bg-dark">

    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="#" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">

            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${pageContext.request.contextPath}/index.xhtml" class="nav-link px-2 text-white">Pagina principal</a></li>
                <li><a href="${pageContext.request.contextPath}/subasta/busqueda.xhtml" class="nav-link px-2 text-white">Busqueda</a></li>
                <li><a href="${pageContext.request.contextPath}/subasta/verSubastas.xhtml" class="nav-link px-2 text-white">Ver tus subastas</a></li>
            </ul>

            <div class="text-end">

                <a href="${pageContext.request.contextPath}/usuario/PaginaPerfil.xhtml">
                    <button type="button" class="btn btn-warning">Ver Perfil</button>
                </a>
            </div>
        </div>

    </div>

</header>

<main>

    <div class="p-3 mb-lg-3 text-emphasis-dark">

        <div class="row">
            <div class="col">
                <table class="table col border border-primary border-3 bg-white">
                    <thead>
                    <tr>
                        <td colspan="1"> <h4> ${movil.marca} ${movil.modelo} </h4> </td>
                    </tr>
                    </thead>

                    <tbody>

                        <tr>
                            <td colspan="1">Imágenes</td>
                        </tr>

                        <tr>
                            <td>Capacidad de la batería: ${movil.capacidadBateria}Ma </td>
                        </tr>

                        <tr>
                            <td>Velocidad de carga: ${movil.velocidadCarga}W</td>
                        </tr>

                        <tr>
                            <td>Tecnología de pantalla: ${movil.tecnologiaPantalla}</td>
                        </tr>

                        <tr>
                            <td>Tasa de refresco de la pantalla: ${movil.tasaRefresco}Hz </td>
                        </tr>

                        <tr>
                            <td>Capacidad de almacenamiento: ${movil.capacidadAlmacenamiento}GB</td>
                        </tr>

                        <tr>
                            <td>Número de unidades de procesamiento: ${movil.numProcesadores} </td>
                        </tr>

                        <tr>
                            <td>Capacidad de memoria RAM: ${movil.capacidadRAM}GB</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <table class="table col border border-primary border-3 bg-white">
                <thead>
                <tr>
                    <td colspan="1" style="text-align: center"> <h4> Usuario que ha publicado esta subasta </h4> </td>
                </tr>
                </thead>

                <tbody>

                    <tr>
                        <td colspan="1">Nombre y apellidos: ${user.nombre} ${user.apellidos} </td>
                    </tr>

                    <tr>
                        <td>Nombre de usuario: ${user.nombreUsuario} </td>
                    </tr>

                    <tr>
                        <td style="text-align: center"> <h5>Información de contacto</h5> </td>
                    </tr>

                    <tr>
                        <td>Correo electrónico: ${user.correoElectronico}</td>
                    </tr>

                    <tr>
                        <td>Número de teléfono: ${user.numeroTelefono} </td>
                    </tr>

                </tbody>
            </table>
        </div>
        <div class="p-2 ">
            <div class="row">
                <div class="col border border-primary border-3 bg-white">

                    <form method="post" class="bg-white container-fluid p-3 mb-lg-3" name="formularioSubasta">
                        <fieldset>
                            <legend>Subasta</legend>

                            <label>Precio actual: ${movil.precioActual}€</label>

                            <br/>

                            <label for="precioProporcionado">Propuesta de precio: </label>
                            <input id="precioProporcionado" type="number" name="precioProporcionado" maxlength="" value="" />
                            <span id="precioProporcionadoInLine" class="form-text">€
                            </span>
                            <div><span class='form-text text-danger'>${errPrecio}</span></div>
                            <input type="hidden" name="formulario" value="formularioSubasta">
                            <br/>

                            <input type="submit" value="Pujar" class="btn btn-primary" />
                        </fieldset>
                    </form>

                </div>
                <div class="col border border-primary border-3 bg-white">
                    <form method="post" class="bg-white container-fluid p-3 mb-lg-3" name="formularioCompra">
                        <fieldset>
                            <legend>Comprar</legend>

                            <label class="fs-4">Precio del dispositivo</label>
                            <br/>
                            <label class="fs-2">${movil.precioCompra}€</label>
                            <input type="hidden" name="formulario" value="formularioCompra">
                            <br/>

                            <input id="comprar" type="submit" value="Comprar" class="btn btn-primary" />
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>

    </div>

</main>

</body>
</html>

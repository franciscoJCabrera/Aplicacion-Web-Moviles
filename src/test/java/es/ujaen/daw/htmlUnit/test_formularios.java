package es.ujaen.daw.htmlUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlunit.DefaultCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.cssparser.parser.CSSException;
import org.htmlunit.cssparser.parser.CSSParseException;
import org.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class test_formularios {

    /**
     * Cambio de la clase DefaultCssErrorHandler para no mostrar errores css en el log
     */
    public static class CSSErrorHandlerSinWarnings extends DefaultCssErrorHandler {

        @Override
        public void error(CSSParseException exception) throws CSSException {
            // No hacemos nada para bloquear el logeo
        }
    }

    private static final Log LOG = LogFactory.getLog(test_formularios.class);


    /**
     * Probamos si la redirección del formulario de busqueda funciona
     */
    @Test
    public void probar_redireccion_formulario_busqueda() {

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            // Cambia la URL por la URL de la página que quieres probar
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/subasta/busqueda.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        // Obtenemos el formulario
        // Se obtiene por el valor de la etiqueta id
        assert page1 != null;
        HtmlForm formulario =
                page1.getFormByName("form_busqueda");

        // Obtenemos el botón de enviar
        // La forma de obtenerlo es: id_del_formulario:id_del_campo
        // Siendo id_del_formulario el valor del ID del formulario que contiene el campo
        // y id_del_campo el valor del ID del campo que se quiere obtener
        final HtmlButton button =
                formulario.getButtonByName("form_busqueda:boton_buscar");

        // Pulsamos enviar el formulario
        // El hacer click nos devuelve la página HTML resultante
        try {
            page2 = button.click();
        } catch (IOException e) {
            LOG.error(e);
        }

        // Vemos si se ha producido la redirección a la otra URL
        assert page2 != null;
        assertEquals("http://localhost:8080/MobileMarket/subasta/resultados_busqueda.xhtml", page2.getBaseURL().toString());

        webClient.close();
    }

    /**
     * Tratar de acceder a vistas protegidas
     */
    @Test
    public void acceder_a_vista_protegidas() {

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        // Mostrar que ha llevado al formulario de iniciar sesión
        // Así obtendremos las credenciales de acceso
        assert page1 != null;
        LOG.info("URL: " + page1.getBaseURL());
        assertEquals("http://localhost:8080/MobileMarket/usuario/PaginaInicioSesion.xhtml", page1.getBaseURL().toString());

        // Obtenemos el formulario de inicio de sesión por si ID
        HtmlForm formularioInicioSesion = page1.getFormByName("inicio_sesion");

        // Obtenemos el campo de nombre, de la contraseña y el botón de enviar el formulario
        HtmlEmailInput correo = formularioInicioSesion.getInputByName("inicio_sesion:correo");
        HtmlPasswordInput password = formularioInicioSesion.getInputByName("inicio_sesion:contrasenia");
        final HtmlButton buttonInicioSesion =
                formularioInicioSesion.getButtonByName("inicio_sesion:boton_enviar");

        // Le ponemos los valores a los campos de correo y contraseña
        // En este caso los ded un usuario definido por defecto
        correo.setValueAttribute("fjcb0015@red.ujaen.es");
        password.setValueAttribute("Secret.1");
        try {
            page1 = buttonInicioSesion.click();
            // Mostramos la redirección
            // Al rellenar bien el formulario de inicio de sesión, se te redirige a la página principal
            assertEquals("http://localhost:8080/MobileMarket/index.xhtml", page1.getBaseURL().toString());

        } catch (IOException e) {
            LOG.error(e);
        }

        // Volvemos a acceder a la vista protegida
        try {
            page2 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }
        assert page2 != null;
        LOG.info("URL: " + page2.getBaseURL());

        // Vemos si hemos podido acceder
        // Si la URL es la de la página protegida, es porque tenemos las credenciales necesarias para acceder a ella
        assertEquals("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml", page2.getBaseURL().toString());

        // Vemos si los datos del usuario son correctos
        // Son pruebas para ver que se muestran correctamente los datos de la persona que hemos usado las credenciales
        HtmlForm form_datos = page2.getFormByName("datos_personales");
        HtmlTextInput text_nombre = form_datos.getInputByName("datos_personales:Nombre");
        assertEquals("Jose", text_nombre.getValue());

        webClient.close();
    }

    /**
     * Metodo en el cual se realiza la prueba de los siguientes elementos de HtmlUnit:
     * - TextArea
     * - HtmlCheckBox
     * - HtmlSelect y HtmlOption
     */
    @Test
    public void pruebaFormulariosVarias(){

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaRegistro.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        // Obtenermos el formulario
        assert page1 != null;
        HtmlForm formulario =
                page1.getFormByName("vistaRegistro");

        ///Obtenemos el textArea
        final HtmlTextArea textArea = formulario.getTextAreaByName("descripcionUsuario");

        textArea.setText("Descripcion de prueba");

        ///Hacemos la prueba ahora con el checkBox
        final HtmlCheckBoxInput cambios = formulario.getInputByName("vistaRegistro:save-info");
        cambios.setChecked(true);

        ///Hacemos la prueba ahora con el select/option
        HtmlSelect select = formulario.getSelectByName("vistaRegistro:paises_input");
        HtmlOption option = select.getOptionByValue("Francia");
        select.setSelectedAttribute(option, true);

        webClient.close();

    }

    /**
     * Metodo en el cual obtenemos objetos (input en este caso) gracias al arbol DOM
     * @throws Exception
     */
    @Test
    public void getElements() throws Exception {

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaRegistro.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        ///Obtenemos todos los elementos que sean "input" del arbol DOM (arbol que almacena toda la estructura de una vista proporcionada)
        final NodeList div = page1.getElementsByTagName("input");
        DomNodeList<DomElement> lista = page1.getElementsByTagName("input");

        ///Mostramos los elementos en consola
        for( DomElement elemento : lista){
            LOG.info(elemento.asXml());
        }
        webClient.close();

    }

    /**
     * Obtencion de elementos mediante XPath
     * @throws Exception
     */
    @Test
    public void xpath() throws Exception{

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaRegistro.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        ///Ejemplo de obtencion de todos aquellos elementos que sean div, formato: //x. Se muestra todo su contenido tambien

        /*
        final List<DomNode> divs = page1.getByXPath("//div");
        for( DomNode elemento : divs){
            LOG.info(elemento.asXml());
        }*/

        ///Ejemplo de obtencion de los elementos que sean div y que ademas tenga como id "nombreUsuario" concretamente
        final List<DomNode> divs = page1.getByXPath("//div[@id='nombreUsuario']");
        for( DomNode elemento : divs){
            LOG.info(elemento.asXml());
        }
        webClient.close();

    }


    /**
     * Prueba para la validación en un formulario
     */
    @Test
    public void pruebaValidacion() {

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;
        HtmlPage page2 = null;

        // Obtenemos la pagina html
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        // Mostrar que ha llevado al formulario de iniciar sesion
        assert page1 != null;
        LOG.info("URL: " + page1.getBaseURL());
        assertEquals("http://localhost:8080/MobileMarket/usuario/PaginaInicioSesion.xhtml", page1.getBaseURL().toString());

        // Obtenemos el formulario de inicio de sesion
        HtmlForm formularioInicioSesion = page1.getFormByName("inicio_sesion");

        // Obtenemos el campo de nombre y de contraseña y el boton
        HtmlEmailInput correo = formularioInicioSesion.getInputByName("inicio_sesion:correo");
        HtmlPasswordInput password = formularioInicioSesion.getInputByName("inicio_sesion:contrasenia");
        final HtmlButton buttonInicioSesion =
                formularioInicioSesion.getButtonByName("inicio_sesion:boton_enviar");

        correo.setValueAttribute("fjcb0015@red.ujaen.es");
        password.setValueAttribute("Secret.1");
        try {
            page1 = buttonInicioSesion.click();
            // Mostramos la redireccion
            assertEquals("http://localhost:8080/MobileMarket/index.xhtml", page1.getBaseURL().toString());

        } catch (IOException e) {
            LOG.error(e);
        }

        // Volvemos a acceder a la vista protegida
        try {
            page2 = webClient.getPage("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }
        assert page2 != null;
        LOG.info("URL: " + page2.getBaseURL());

        // Vemos si hemos podido acceder
        assertEquals("http://localhost:8080/MobileMarket/usuario/PaginaPerfil.xhtml", page2.getBaseURL().toString());

        // Cogemos el formulario que queremos comprobar
        HtmlForm form_datos = page2.getFormByName("datos_personales");
        HtmlTextInput text_dni =
                form_datos.getInputByName("datos_personales:DNI");
        HtmlTextInput text_telefono =
                form_datos.getInputByName("datos_personales:Telefono");

        // Ponemos datos incorrectos para ver los mensajes de error que nos tiene que dar
        text_dni.setValueAttribute("J");
        text_telefono.setValueAttribute("1234");

        // Enviamos el formulario
        HtmlButton boton_enviar =
                form_datos.getButtonByName("datos_personales:guardar_cambios");
        HtmlPage respuesta;
        try {
            respuesta = boton_enviar.click();
            // Obtenemos los errores de validación, los cuales se almacenan en etiquetas span con clase de estilos
            // invalid-feedback, así que buscamos todos esos elementos usando XPath
            final List<HtmlElement> lista_errores =
                    respuesta.getByXPath("//*[@class='invalid-feedback']");
            if(!lista_errores.isEmpty()) {
                LOG.error("Errores de validación:");
            }
            // Mostramos los errores por el LOG
            for(HtmlElement element : lista_errores) {
                LOG.error(element.asXml());
            }
        } catch (IOException e) {
            LOG.error(e);
        }

        webClient.close();

    }

    @Test
    public void test_JS_click() {

        WebClient webClient = new WebClient();

        // Eliminamos los logs de los errores css de bootstrap y primefaces ya que no son nuestros estilos
        webClient.setCssErrorHandler(new CSSErrorHandlerSinWarnings());

        HtmlPage page1 = null;

        // Obtenemos la página
        try {
            page1 = webClient.getPage("http://localhost:8080/MobileMarket/VistaTest/test_js.xhtml");
        } catch (Exception e) {
            LOG.error(e);
        }

        // Vemos que no existe el nodo nuevoP que tiene la id de newButton
        assert page1 != null;
        assertNull( page1.getElementById("newButton") );

        // Obtenemos el botón por su id
        HtmlSubmitInput boton = (HtmlSubmitInput) page1.getElementById("boton");

        // Pulsamos el botón
        try {
            boton.click();
        } catch (IOException e) {
            LOG.error(e);
        }

        // Ahora vemos si el nodo se ha creado como determina el Js
        assertNotNull( page1.getElementById("newButton") );

        // Vemos que el mensaje es el que se produce al pulsar el botón solo una vez
        assertEquals("Has pulsado un boton", page1.getElementById("newButton").asNormalizedText() );

        // Lo pulsamos nuevamente
        try {
            boton.click();
        } catch (IOException e) {
            LOG.error(e);
        }

        // Vemos que el mensaje ha cambiado al del pulsar el botón más de una vez
        assertEquals("Deja de pulsarme", page1.getElementById("newButton").asNormalizedText() );

        // Vemos la clase de estilos del botón
        LOG.info( boton.getAttribute("class"));
        assertEquals("btn btn-lg btn-primary", boton.getAttribute("class"));

        // Simulamos que le ponemos el ratón encima para provocar el evento de mouseOver
        boton.mouseOver();

        // Volvemos a ver la clase de estilos para ver si ha cambiado
        LOG.info( boton.getAttribute("class"));
        assertEquals("btn btn-lg btn-secondary", boton.getAttribute("class"));

        webClient.close();

    }

}

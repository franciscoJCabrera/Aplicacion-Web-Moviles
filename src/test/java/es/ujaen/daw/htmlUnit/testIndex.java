package es.ujaen.daw.htmlUnit;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class homePage {

    @Test
    public void obtenerTitulo() throws Exception {

        WebClient webClient = new WebClient();
        HtmlPage page = null;
        try {
            page = webClient.getPage("http://localhost:8080/MobileMarket/index.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("Mobile Market", page.getTitleText());
        webClient.close();

    }

    @Test
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://www.htmlunit.org/");
            assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

            final String pageAsXml = page.asXml();
            assertTrue(pageAsXml.contains("<body class=\"topBarDisabled\">"));

            final String pageAsText = page.asNormalizedText();
            assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }
    }
}
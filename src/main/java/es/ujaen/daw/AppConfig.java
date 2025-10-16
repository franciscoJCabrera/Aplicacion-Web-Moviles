package es.ujaen.daw;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

//@DatabaseIdentityStoreDefinition(
//        dataSourceLookup = "java:global/jdbc/mobile",
//        callerQuery = "select contrasenia from Usuario where correoElectronico = ?",
//        groupsQuery = "select grupo from Usuario where correoElectronico = ?" ,
//        hashAlgorithmParameters = {
//                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
//                "Pbkdf2PasswordHash.Iterations=3072",
//                "Pbkdf2PasswordHash.SaltSizeBytes=64"
//        }
//)

@EmbeddedIdentityStoreDefinition({
        @Credentials(callerName = "admin@red.ujaen.es", password = "Secret.1", groups = {"ADMINISTRADORES"}),
        @Credentials(callerName = "users@red.ujaen.es", password = "Secret.2", groups = {"USUARIOS"}),
        }
)

///@BasicAuthenticationMechanismDefinition( realmName = "Catalogo de moviles")
///@FormAuthenticationMechanismDefinition(
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/usuario/PaginaInicioSesion.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@ApplicationScoped
@FacesConfig
public class AppConfig {
}

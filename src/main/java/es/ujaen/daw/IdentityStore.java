package es.ujaen.daw;


import es.ujaen.daw.enity.Usuario;
import es.ujaen.daw.model.UsuarioDao;
import es.ujaen.daw.qualifiers.UsuarioJPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static java.util.Arrays.asList;

/**
 * Sample Customized JEE Security IdentityStore
 *
 * @author jrbalsas
 */
@ApplicationScoped
public class IdentityStore implements jakarta.security.enterprise.identitystore.IdentityStore {

    private static final Logger logger = Logger.getLogger(IdentityStore.class.getName());

    @Inject
    private AuthService authService;

    @Inject @UsuarioJPA
    private UsuarioDao clientesDAO;

    public IdentityStore() {
    }

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {

        String username = usernamePasswordCredential.getCaller();
        String password = usernamePasswordCredential.getPasswordAsString();

        boolean authenticated=false;

        //Look for user
        Usuario cliente= clientesDAO.buscarUsuarioCorreo(username);

        //Check password with encrypted version
        if (cliente!=null && authService.verifyPassword(password, cliente.getContraCifrada())) {
            authenticated=true;
            logger.info(String.format("Authenticated user %s", username));
        } else {
            logger.warning(String.format("Authentication error for %s", username));
        }
        // If valid credentials get user permissions and inform to the Application Server
        if ( authenticated  ) {
            //Get roles for valid user...
            Set<String> roles =  new HashSet<>(asList( cliente.getGrupo() ));

            //Authenticate user in Application Server and pass custom User information for Server Principal Object
            // return new CredentialValidationResult( new ClubPrincipal(cliente), roles);
            return new CredentialValidationResult( username, roles); //Server only needs username and roles to create default Principal object
        }

        //Credentials invalid
        return INVALID_RESULT;
    }
}

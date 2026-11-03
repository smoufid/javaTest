package ma.org.anp.security;

 
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AuthentificationRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    
    @Value("${ldap.url}")
    private String ldapUrl;
    
    @Value("${ldap.base}")
    private String ldapBase;
    
    @Autowired
    private AuthentificationRepository utilisateurRepository;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        logger.info("Tentative d'authentification pour l'utilisateur: {}", username);
        
        // 1. Authentifier contre LDAP/AD
        if (!authenticateAgainstLdap(username, password)) {
            logger.error("Échec de l'authentification LDAP pour l'utilisateur: {}", username);
            throw new BadCredentialsException("Identifiants LDAP invalides");
        }
        
        logger.info("Authentification LDAP réussie pour l'utilisateur: {}", username);
        
        // 2. Vérifier que l'utilisateur existe dans la base de données
        Utilisateurs utilisateur = utilisateurRepository.findByUtilisateur(username)
                .orElseThrow(() -> {
                    logger.error("Utilisateur {} authentifié dans AD mais non trouvé dans la base de données", username);
                    return new UsernameNotFoundException(
                        "Utilisateur authentifié dans AD mais non trouvé dans la base de données: " + username);
                });
        
        logger.info("Utilisateur {} trouvé dans la base de données", username);
        
        // 3. Créer l'objet UserDetails avec les informations de la base
        CustomUserDetails userDetails = CustomUserDetails.build(utilisateur, password);
        
        logger.info("Authentification complète réussie pour l'utilisateur: {}", username);
        
        return new UsernamePasswordAuthenticationToken(
            userDetails, 
            password, 
            userDetails.getAuthorities()
        );
    }
    
    /**
     * Authentifie un utilisateur contre LDAP/Active Directory
     * Essaie plusieurs formats d'authentification en fallback
     * @param username le nom d'utilisateur
     * @param password le mot de passe
     * @return true si l'authentification réussit
     */
    private boolean authenticateAgainstLdap(String username, String password) {
        // Méthode 1 : Essayer avec le format UPN (User Principal Name)
        logger.debug("Tentative d'authentification LDAP - Méthode 1: Format UPN");
        if (tryLdapAuthentication(username + "@anp.ma", password)) {
            logger.info("Authentification réussie avec le format UPN pour: {}", username);
            return true;
        }
        
        // Méthode 2 : Essayer avec le nom d'utilisateur simple (sAMAccountName)
        logger.debug("Tentative d'authentification LDAP - Méthode 2: sAMAccountName");
        if (tryLdapAuthentication(username, password)) {
            logger.info("Authentification réussie avec sAMAccountName pour: {}", username);
            return true;
        }
        
        // Méthode 3 : Essayer avec le DN complet
        logger.debug("Tentative d'authentification LDAP - Méthode 3: DN complet");
        String userDn = "CN=" + escapeDnValue(username) + "," + ldapBase;
        if (tryLdapAuthentication(userDn, password)) {
            logger.info("Authentification réussie avec DN complet pour: {}", username);
            return true;
        }
        
        logger.error("Échec de l'authentification LDAP pour l'utilisateur {} avec toutes les méthodes", username);
        return false;
    }
    
    /**
     * Tente une authentification LDAP avec un principal donné
     * @param principal le principal à utiliser (UPN, sAMAccountName, ou DN)
     * @param password le mot de passe
     * @return true si l'authentification réussit
     */
    private boolean tryLdapAuthentication(String principal, String password) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, password);
        
        logger.debug("Tentative de connexion LDAP avec principal: {}", principal);
        logger.debug("URL LDAP: {}", ldapUrl);
        
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            logger.debug("Connexion LDAP établie avec succès pour: {}", principal);
            return true;
        } catch (NamingException e) {
            logger.debug("Échec de connexion LDAP pour {}: {} - {}", principal, e.getClass().getSimpleName(), e.getMessage());
            return false;
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.warn("Erreur lors de la fermeture du contexte LDAP: {}", e.getMessage());
                }
            }
        }
    }
    
    /** Échappe les caractères spéciaux RFC 4514 pour un composant de DN LDAP. */
    private static String escapeDnValue(String value) {
        if (value == null) return "";
        StringBuilder sb = new StringBuilder(value.length() * 2);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\\': case ',': case '+': case '"':
                case '<':  case '>': case ';':
                    sb.append('\\').append(c);
                    break;
                case '#':
                    // '#' doit être échappé uniquement en début de valeur
                    if (i == 0) sb.append('\\');
                    sb.append(c);
                    break;
                case ' ':
                    // espace doit être échappé en début et fin de valeur
                    if (i == 0 || i == value.length() - 1) sb.append('\\');
                    sb.append(c);
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

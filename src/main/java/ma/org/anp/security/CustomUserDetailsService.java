package ma.org.anp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AuthentificationRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AuthentificationRepository utilisateurRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateurs utilisateur = utilisateurRepository.findByUtilisateur(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom: " + username));
        
        // Le mot de passe sera géré par LDAP, donc on utilise un placeholder
        return CustomUserDetails.build(utilisateur, "");
    }
    
    /**
     * Charge un utilisateur par son ID
     * @param id l'ID de l'utilisateur
     * @return UserDetails
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Utilisateurs utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
        
        return CustomUserDetails.build(utilisateur, "");
    }
}

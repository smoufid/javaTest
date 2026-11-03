package ma.org.anp.service.administration;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ma.org.anp.model.dto.LoginRequest;
import ma.org.anp.model.dto.LoginResponse;
import ma.org.anp.model.dto.UserInfoDTO;
import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AuthentificationRepository;
import ma.org.anp.security.CustomUserDetails;
import ma.org.anp.security.JwtTokenProvider;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Autowired
    private MfaService mfaService;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        logger.debug("Début authentification: {}", loginRequest.getUsername());

        try {
            // 1. Authentification LDAP
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. Récupérer l'utilisateur en base
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Utilisateurs utilisateur = authentificationRepository
                    .findByUtilisateur(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // 3. MFA activé ? → retourner un token temporaire
            if (utilisateur.getMfaEnabled()) {
                logger.info("MFA requis pour: {}", loginRequest.getUsername());
                String tempToken = tokenProvider.generateTempToken(userDetails.getUsername());
                return new LoginResponse(tempToken, true);
            }

            // 4. MFA non activé → retourner le JWT final directement
            String jwt = tokenProvider.generateToken(authentication);
            UserInfoDTO userInfo = new UserInfoDTO(utilisateur);
            logger.info("Authentification réussie (sans MFA): {}", loginRequest.getUsername());
            return new LoginResponse(jwt, userInfo);

        } catch (Exception e) {
            logger.error("Erreur authentification: {}", loginRequest.getUsername(), e);
            throw e;
        }
    }

    // ─── Vérification du code MFA ─────────────────────────────────────────────

    public LoginResponse verifyMfa(String tempToken, String code) {

    // 1. Valider que c'est bien un token temporaire
    if (!tokenProvider.validateToken(tempToken) || !tokenProvider.isTempToken(tempToken)) {
        throw new RuntimeException("Token temporaire invalide ou expiré");
    }

    // 2. Récupérer l'utilisateur depuis le token
    String username = tokenProvider.getUsernameFromToken(tempToken);
    Utilisateurs utilisateur = authentificationRepository
            .findByUtilisateur(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    // 3. Vérifier le code TOTP
    if (!mfaService.verifyCode(utilisateur.getMfaSecret(), code)) {
        throw new RuntimeException("Code MFA invalide");
    }

    // 4. Générer le JWT final
    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

    String jwt = tokenProvider.generateTokenFromUsername(
            username,
            utilisateur.getId(),
            utilisateur.getAdministration(),
            utilisateur.getServeurRun()
    );

    UserInfoDTO userInfo = new UserInfoDTO(utilisateur);
    logger.info("MFA vérifié avec succès pour: {}", username);
    return new LoginResponse(jwt, userInfo);
}

    public UserInfoDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Aucun utilisateur authentifié");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Utilisateurs utilisateur = authentificationRepository
                .findByUtilisateur(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return new UserInfoDTO(utilisateur);
    }
}
package ma.org.anp.controller.authentification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.org.anp.model.dto.LoginRequest;
import ma.org.anp.model.dto.LoginResponse;
import ma.org.anp.model.dto.UserInfoDTO;
import ma.org.anp.service.administration.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "API d'authentification avec AD/LDAP et JWT")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final String COOKIE_NAME = "access_token";

    @Autowired
    private AuthService authService;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${app.cookie.secure:true}")
    private boolean cookieSecure;

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur",
               description = "Authentifie un utilisateur via AD/LDAP et place le token JWT dans un cookie HttpOnly")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                               HttpServletResponse httpResponse) {
        logger.info("Tentative de connexion pour l'utilisateur: {}", loginRequest.getUsername());
        LoginResponse response = authService.authenticateUser(loginRequest);

        if (!response.isMfaRequired() && response.getToken() != null) {
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, buildJwtCookie(response.getToken()).toString());
            response.setToken(null);
        }

        logger.info("Connexion réussie pour l'utilisateur: {}", loginRequest.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Invalide le cookie JWT")
    public ResponseEntity<Void> logout(HttpServletResponse httpResponse) {
        ResponseCookie expiredCookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Informations utilisateur",
               description = "Récupère les informations de l'utilisateur actuellement connecté",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserInfoDTO> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Vérifie que l'API est opérationnelle")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API Backend Echanges - Service d'authentification opérationnel");
    }

    private ResponseCookie buildJwtCookie(String token) {
        String safeToken = token != null ? token : "";
        Duration maxAge = Objects.requireNonNull(Duration.ofMillis(jwtExpirationMs));
        return ResponseCookie.from(COOKIE_NAME, safeToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Strict")
                .path("/")
                .maxAge(maxAge)
                .build();
    }
}

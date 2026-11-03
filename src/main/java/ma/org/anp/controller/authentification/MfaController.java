package ma.org.anp.controller.authentification;

import ma.org.anp.model.dto.LoginResponse;
import ma.org.anp.model.dto.MfaSetupResponse;
import ma.org.anp.model.dto.MfaVerifyRequest;
import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AuthentificationRepository;
import ma.org.anp.service.administration.AuthService;
import ma.org.anp.service.administration.MfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class MfaController {

    private static final String COOKIE_NAME = "access_token";

    @Autowired
    private MfaService mfaService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${app.cookie.secure:true}")
    private boolean cookieSecure;

    // ─── Vérification code MFA après login ───────────────────────────────────

    @PostMapping("/verify-mfa")
    public ResponseEntity<LoginResponse> verifyMfa(@RequestBody MfaVerifyRequest request,
                                                   HttpServletResponse httpResponse) {
        LoginResponse response = authService.verifyMfa(request.getTempToken(), request.getCode());
        if (response.getToken() != null) {
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, buildJwtCookie(response.getToken()).toString());
            response.setToken(null);
        }
        return ResponseEntity.ok(response);
    }

    // ─── Setup : générer secret + otpAuthUrl ─────────────────────────────────

    @PostMapping("/mfa/setup")
    public ResponseEntity<MfaSetupResponse> setupMfa() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String secret = mfaService.generateSecret();
        String otpAuthUrl = mfaService.generateOtpAuthUrl(username, secret);

        Utilisateurs user = authentificationRepository.findByUtilisateur(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setMfaSecret(secret);
        authentificationRepository.save(user);

        return ResponseEntity.ok(new MfaSetupResponse(otpAuthUrl));
    }

    // ─── Confirmation activation MFA ─────────────────────────────────────────

    @PostMapping("/mfa/confirm")
    public ResponseEntity<String> confirmMfa(@RequestParam String code) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Utilisateurs user = authentificationRepository.findByUtilisateur(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!mfaService.verifyCode(user.getMfaSecret(), code)) {
            return ResponseEntity.badRequest().body("Code MFA invalide");
        }

        mfaService.enableMfa(username, user.getMfaSecret());
        return ResponseEntity.ok("MFA activé avec succès");
    }

    // ─── Désactivation MFA ───────────────────────────────────────────────────

    @PostMapping("/mfa/disable")
    public ResponseEntity<String> disableMfa(@RequestParam String code) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Utilisateurs user = authentificationRepository.findByUtilisateur(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!mfaService.verifyCode(user.getMfaSecret(), code)) {
            return ResponseEntity.badRequest().body("Code MFA invalide");
        }

        mfaService.disableMfa(username);
        return ResponseEntity.ok("MFA désactivé avec succès");
    }

    @GetMapping("/mfa/time")
    public ResponseEntity<String> serverTime() {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        return ResponseEntity.ok(
                "Timestamp: " + System.currentTimeMillis() +
                        " | TimeIndex: " + timeIndex +
                        " | Heure: " + new java.util.Date());
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
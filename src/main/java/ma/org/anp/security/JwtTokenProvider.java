package ma.org.anp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private static final long TEMP_TOKEN_EXPIRATION = 5 * 60 * 1000L;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateTempToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("mfaStatus", "MFA_PENDING")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TEMP_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTempToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return "MFA_PENDING".equals(claims.get("mfaStatus", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("userId", userPrincipal.getUserId())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateTokenFromUsername(String username, Long userId,
                                            String typeUtilisateur, String codeSociete) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("typeUtilisateur", typeUtilisateur)
                .claim("codeSociete", codeSociete)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

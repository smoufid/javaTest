package ma.org.anp.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bloque les tentatives de login après 5 échecs en 5 minutes par adresse IP.
 */
@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private static final int  MAX_ATTEMPTS = 5;
    private static final long WINDOW_MS    = 5 * 60 * 1000L;
    private static final String LOGIN_PATH = "/api/auth/login";

    // IP -> [windowStart, attemptCount]
    private final ConcurrentHashMap<String, long[]> attempts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(request.getMethod())
                && request.getServletPath().equals(LOGIN_PATH)) {

            String ip  = resolveClientIp(request);
            long   now = System.currentTimeMillis();

            long[] entry = attempts.compute(ip, (k, v) -> {
                if (v == null || now - v[0] > WINDOW_MS) {
                    return new long[]{now, 1};
                }
                v[1]++;
                return v;
            });

            if (entry[1] > MAX_ATTEMPTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(
                    "{\"error\":\"Trop de tentatives d'authentification. R\\u00e9essayez dans 5 minutes.\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        return (xff != null && !xff.isEmpty()) ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }
}

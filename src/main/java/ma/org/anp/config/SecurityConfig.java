package ma.org.anp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ma.org.anp.security.CustomAuthenticationProvider;
import ma.org.anp.security.JwtAuthenticationFilter;
import ma.org.anp.security.LoginRateLimitFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private LoginRateLimitFilter loginRateLimitFilter;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

/*     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();

        http
            .authenticationProvider(customAuthenticationProvider)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(
                    "/api/auth/login",
                    "/api/auth/logout",
                    "/api/auth/verify-mfa",
                    "/api/auth/health",
                    "/api/auth/mfa/time",
                    "/api/auth/mfa/setup",
                    "/api/auth/mfa/confirm",
                    "/api/auth/mfa/disable")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(csrfHandler))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers
                .frameOptions(frame -> frame.deny())
                .contentTypeOptions(ct -> {})
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                .contentSecurityPolicy(csp -> csp.policyDirectives(
                    "default-src 'self' anp.ma *.anp.ma; " +
                    "script-src 'self' anp.ma *.anp.ma; " +
                    "style-src 'self' 'unsafe-inline' anp.ma *.anp.ma https://fonts.googleapis.com; " +
                    "img-src 'self' data: blob: anp.ma *.anp.ma; " +
                    "font-src 'self' data: anp.ma *.anp.ma https://fonts.gstatic.com; " +
                    "connect-src 'self' anp.ma *.anp.ma https://srvwebedi.anp.ma http://localhost:8080 https://srvwebeditest.anp.ma; " +
                    "frame-ancestors 'self' anp.ma *.anp.ma;"))
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/health").permitAll()
                .requestMatchers("/api/auth/verify-mfa").permitAll()
                .requestMatchers("/api/auth/mfa/time").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(loginRateLimitFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    } */


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authenticationProvider(customAuthenticationProvider)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())  // ✓ Sûr avec JWT
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(headers -> headers
            .frameOptions(frame -> frame.deny())
            .contentTypeOptions(ct -> {})
            .referrerPolicy(referrer -> referrer
                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
            .contentSecurityPolicy(csp -> csp.policyDirectives(
                "default-src 'self' anp.ma *.anp.ma; " +
                "script-src 'self' anp.ma *.anp.ma; " +
                "style-src 'self' 'unsafe-inline' anp.ma *.anp.ma https://fonts.googleapis.com; " +
                "img-src 'self' data: blob: anp.ma *.anp.ma; " +
                "font-src 'self' data: anp.ma *.anp.ma https://fonts.gstatic.com; " +
                "connect-src 'self' anp.ma *.anp.ma https://srvwebedi.anp.ma https://srvwebeditest.anp.ma; " +
                "frame-ancestors 'self'; " +
                "base-uri 'self'; " +
                "form-action 'self';"))
            .httpStrictTransportSecurity(hsts -> hsts
                .includeSubDomains(true)
                .preload(true)
                .maxAgeInSeconds(31536000)))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/auth/login", "/api/auth/health").permitAll()
            .requestMatchers("/api/auth/verify-mfa").permitAll()
            .requestMatchers("/api/auth/mfa/time").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(loginRateLimitFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        if (allowedOrigins == null || allowedOrigins.trim().isEmpty()) {
            configuration.addAllowedOrigin("*");
            configuration.setAllowCredentials(false);
        } else {
            List<String> origins = Arrays.asList(allowedOrigins.split(","));
            configuration.setAllowedOrigins(origins);
            configuration.setAllowCredentials(true);
        }

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "X-XSRF-TOKEN", "Set-Cookie"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

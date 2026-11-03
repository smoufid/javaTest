package ma.org.anp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Arrays.asList(
                        // Serveur local pour développement
                        new Server()
                                .url("http://localhost:8080" + contextPath)
                                .description("Serveur de développement local (HTTP)"),
                        // Serveur de test HTTP
                        new Server()
                                .url("https://srvwebedi.anp.ma" + contextPath)
                                .description("Serveur de test (HTTP)")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Entrez le token JWT (sans le préfixe 'Bearer')")))
                .info(new Info()
                        .title("API Gestion Echanges EDI")
                        .version("1.0.0")
                        .description("API REST pour la gestion des Flux EDI avec authentification AD/LDAP et JWT")
                        .contact(new Contact()
                                .name("ANP")
                                .email("support@anp.ma")
                                .url("https://www.anp.ma"))
                        .license(new License()
                                .name("Propriétaire")
                                .url("https://www.anp.ma")));
    }
}

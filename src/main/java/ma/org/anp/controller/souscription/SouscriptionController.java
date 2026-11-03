package ma.org.anp.controller.souscription;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.org.anp.model.entity.souscription.SouscriptionSimple;
import ma.org.anp.service.souscription.SouscriptionService;

@RestController
@RequestMapping("/api/souscription")
@Tag(name = "Souscription", description = "API de gestion des souscriptions")
@SecurityRequirement(name = "bearerAuth")
public class SouscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SouscriptionController.class);
    @Autowired
    private SouscriptionService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<SouscriptionSimple>> listerType() {
        List<SouscriptionSimple> utilisateurs = administrationService.listerParJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroLloyd}")
    @Operation(summary = "Lister les souscriptions par numéro", description = "Récupère la liste des souscriptions correspondant au numéro fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<SouscriptionSimple>> listerparLloyd(@PathVariable String numeroLloyd) {
        List<SouscriptionSimple> utilisateurs = administrationService.listerParNumLloyd(numeroLloyd);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer une souscription par ID", description = "Récupère une souscription spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<SouscriptionSimple> getSouscriptionById(@PathVariable Long id) {
        SouscriptionSimple souscription = administrationService.listerParID(id);
        return ResponseEntity.ok(souscription);
    }

}

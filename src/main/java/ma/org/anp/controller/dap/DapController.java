package ma.org.anp.controller.dap;

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
import ma.org.anp.model.entity.dap.DapSimple;
import ma.org.anp.service.dap.DapService;

@RestController
@RequestMapping("/api/dap")
@Tag(name = "Dap", description = "API de gestion des Dap")
@SecurityRequirement(name = "bearerAuth")
public class DapController {
    private static final Logger logger = LoggerFactory.getLogger(DapController.class);
    @Autowired
    private DapService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<DapSimple>> listerType() {
        List<DapSimple> utilisateurs = administrationService.listerAvisJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroMessage}")
    @Operation(summary = "Lister les avis par numéro", description = "Récupère la liste des avis correspondant au numéro fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<DapSimple>> listerparAvis(@PathVariable String numeroMessage) {
        List<DapSimple> utilisateurs = administrationService.listerParNumAvis(numeroMessage);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer un avis par ID", description = "Récupère un avis spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<DapSimple> getAvisById(@PathVariable Long id) {
        DapSimple avis = administrationService.listerParID(id);
        return ResponseEntity.ok(avis);
    }

}

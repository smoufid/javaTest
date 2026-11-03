package ma.org.anp.controller.demandeaccostage;
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
import ma.org.anp.model.entity.demandeaccostage.DemandeAccostageSimple;
import ma.org.anp.service.demandeaccostage.DemandeAccostageService;

@RestController
@RequestMapping("/api/demandeaccostage")
@Tag(name = "DemandeAccostage", description = "API de gestion des demandes d'accostage")
@SecurityRequirement(name = "bearerAuth")
public class DemandeAccostageController {
    private static final Logger logger = LoggerFactory.getLogger(DemandeAccostageController.class);
    @Autowired
    private DemandeAccostageService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<DemandeAccostageSimple>> listerType() {
        List<DemandeAccostageSimple> utilisateurs = administrationService.listerParJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroEscale}")
    @Operation(summary = "Lister les demandes d'accostage par numéro", description = "Récupère la liste des demandes d'accostage correspondant au numéro fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<DemandeAccostageSimple>> listerparLloyd(@PathVariable long numeroEscale) {
        List<DemandeAccostageSimple> utilisateurs = administrationService.listerParNumEscale(numeroEscale);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer une demande d'accostage par ID", description = "Récupère une demande d'accostage spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<DemandeAccostageSimple> getDemandeAccostageById(@PathVariable Long id) {
        DemandeAccostageSimple demande = administrationService.listerParID(id);
        return ResponseEntity.ok(demande);
    }

}

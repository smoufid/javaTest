package     ma.org.anp.controller.attestationescale;
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

import ma.org.anp.model.entity.attestationescale.AttestationEscaleSimple;
import ma.org.anp.service.attestationescale.AttestationEscaleService;


@RestController
@RequestMapping("/api/attestationescale")
@Tag(name = "AttestationEscale", description = "API de gestion des attestations d'échelle")
@SecurityRequirement(name = "bearerAuth")
public class AttestationEscaleController {
    private static final Logger logger = LoggerFactory.getLogger(AttestationEscaleController.class);
    @Autowired
    private AttestationEscaleService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<AttestationEscaleSimple>> listerType() {
        List<AttestationEscaleSimple> utilisateurs = administrationService.listerParJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroEscale}")
    @Operation(summary = "Lister les mouvements par numéro", description = "Récupère la liste des mouvements correspondant au numéro fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<AttestationEscaleSimple>> listerparNumeroAvis(@PathVariable long numeroEscale) {
        List<AttestationEscaleSimple> utilisateurs = administrationService.listerParEscale(numeroEscale);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer un mouvement par ID", description = "Récupère un mouvement spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<AttestationEscaleSimple> getMouvementById(@PathVariable Long id) {
        AttestationEscaleSimple mouvement = administrationService.listerParID(id);
        return ResponseEntity.ok(mouvement);
    }

}

package     ma.org.anp.controller.mouvements;
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
import ma.org.anp.model.entity.mouvement.MouvementsSimple;
import ma.org.anp.service.mouvements.MouvementsService;


@RestController
@RequestMapping("/api/mouvements")
@Tag(name = "Mouvements", description = "API de gestion des mouvements")
@SecurityRequirement(name = "bearerAuth")
public class MouvementsController {
    private static final Logger logger = LoggerFactory.getLogger(MouvementsController.class);
    @Autowired
    private MouvementsService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<MouvementsSimple>> listerType() {
        List<MouvementsSimple> utilisateurs = administrationService.listerParJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroAvis}")
    @Operation(summary = "Lister les mouvements par numéro", description = "Récupère la liste des mouvements correspondant au numéro fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<MouvementsSimple>> listerparNumeroAvis(@PathVariable String numeroAvis) {
        List<MouvementsSimple> utilisateurs = administrationService.listerParNumLloyd(numeroAvis);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer un mouvement par ID", description = "Récupère un mouvement spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<MouvementsSimple> getMouvementById(@PathVariable Long id) {
        MouvementsSimple mouvement = administrationService.listerParID(id);
        return ResponseEntity.ok(mouvement);
    }

}

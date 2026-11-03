package  ma.org.anp.controller.manifestepassagers;
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
import ma.org.anp.model.entity.manifestepassagers.ManifestePassagersSimple;
import ma.org.anp.service.manifestepassagers.ManifestePassagersService;


@RestController
@RequestMapping("/api/manifeste-passagers")
@Tag(name = "ManifestePassagers", description = "API de gestion des manifestes de passagers")
@SecurityRequirement(name = "bearerAuth")
public class ManifestePassagersController {
    private static final Logger logger = LoggerFactory.getLogger(ManifestePassagersController.class);
    @Autowired
    private ManifestePassagersService administrationService;

    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<ManifestePassagersSimple>> listerType() {
        List<ManifestePassagersSimple> utilisateurs = administrationService.listerParJour();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/liste/{numeroEscale}")
    @Operation(summary = "Lister les manifestes par numéro d'escale", description = "Récupère la liste des manifestes correspondant au numéro d'échelle fourni. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<ManifestePassagersSimple>> listerparNumeroMessage(@PathVariable long numeroEscale) {
        List<ManifestePassagersSimple> utilisateurs = administrationService.listerParNumEscale(numeroEscale);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/getbyId/{id}")
    @Operation(summary = "Récupérer un manifeste par ID", description = "Récupère un manifeste spécifique par son ID. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<ManifestePassagersSimple> getManifesteById(@PathVariable Long id) {
        ManifestePassagersSimple manifeste = administrationService.listerParID(id);
        return ResponseEntity.ok(manifeste);
    }

}

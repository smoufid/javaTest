package ma.org.anp.controller.authentification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.org.anp.model.dto.ClasseOuiNon;
import ma.org.anp.model.dto.UtilisateurListDto;
import ma.org.anp.model.dto.UtilisateurUpsertDto;
import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.service.administration.AdministrationService;

@RestController
@RequestMapping("/api/administration")
@Tag(name = "Administration", description = "API de gestion des utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class AdministrationController {
 private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);
    @Autowired
    private AdministrationService administrationService;

    /**
     * Liste tous les utilisateurs
     * 
     * @return Liste de tous les utilisateurs
     */
    @GetMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tous les utilisateurs",
               description = "Récupère la liste complète des utilisateurs triés par nom. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide"),
            @ApiResponse(responseCode = "403", description = "Accès refusé - rôle ADMIN requis")
    })
    public ResponseEntity<List<UtilisateurListDto>> listerUtilisateurs() {
        List<UtilisateurListDto> utilisateurs =
            Optional.ofNullable(administrationService.listerUtilisateurs())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(UtilisateurListDto::from)
                    .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(utilisateurs);
    }

    /**
     * Crée un nouvel utilisateur
     * 
     * @param utilisateur L'utilisateur à créer
     * @return L'utilisateur créé
     */
    @PostMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un utilisateur",
               description = "Ajoute un nouvel utilisateur avec validation. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou utilisateur déjà existant"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide"),
            @ApiResponse(responseCode = "403", description = "Accès refusé - rôle ADMIN requis")
    })
    public ResponseEntity<?> ajouterUtilisateur(@RequestBody UtilisateurUpsertDto dto) {
        try {
            logger.info("Création utilisateur : {}", dto.getUtilisateur());
            Utilisateurs nouvelUtilisateur = administrationService.ajouterUtilisateur(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UtilisateurListDto.from(nouvelUtilisateur));
        } catch (IllegalArgumentException e) {
             logger.info(e.getMessage(),e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Modifie un utilisateur existant
     * 
     * @param id          L'ID de l'utilisateur à modifier
     * @param utilisateur Les nouvelles données de l'utilisateur
     * @return L'utilisateur modifié
     */
    @PutMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un utilisateur",
               description = "Met à jour les informations d'un utilisateur existant. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou utilisateur inexistant"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide"),
            @ApiResponse(responseCode = "403", description = "Accès refusé - rôle ADMIN requis")
    })
    public ResponseEntity<?> modifierUtilisateur(
            @PathVariable Long id,
            @RequestBody UtilisateurUpsertDto dto) {
        try {
            logger.info("Modification utilisateur id={} : {}", id, dto.getUtilisateur());
            Utilisateurs utilisateurModifie = administrationService.modifierUtilisateur(id, dto);
            logger.info("Utilisateur modifié : {}", utilisateurModifie.getUtilisateur());
            return ResponseEntity.ok(UtilisateurListDto.from(utilisateurModifie));
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Récupère les types d'utilisateurs disponibles
     * 
     * @return Liste des types d'utilisateurs (P=Port, R=Régional, T=Tous les
     *         Ports)
     */

    

    /**
     * Récupère les options Actif (Oui/Non)
     * 
     * @return Liste des options Oui/Non
     */
    @GetMapping("/actif-options")
    @Operation(summary = "Lister les options actif", 
               description = "Récupère les options Oui/Non pour les champs actifs. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<ClasseOuiNon>> listeActifOuiNon() {
        return ResponseEntity.ok(administrationService.listeActifOuiNon());
    }






}

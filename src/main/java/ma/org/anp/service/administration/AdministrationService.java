package ma.org.anp.service.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.dto.ClasseOuiNon;
import ma.org.anp.model.dto.UtilisateurUpsertDto;

import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AdministrationRepository;

@Service
public class AdministrationService {
 private static final Logger logger = LoggerFactory.getLogger(AdministrationService.class);
    @Autowired
    private AdministrationRepository administrationRepository;

    
    /**
     * Ajouter un utilisateur
     *
     * Les champs techniques (id, serveurRun, administration, mfaEnabled) sont
     * fixés ici et non par le client.
     *
     * @param dto Les champs éditables du nouvel utilisateur
     * @return L'utilisateur créé
     * @throws IllegalArgumentException Si l'utilisateur existe déjà ou si les données sont invalides
     */
    public Utilisateurs ajouterUtilisateur(UtilisateurUpsertDto dto) {
        validerChampsObligatoires(dto);

        String nom = dto.getUtilisateur().trim().toUpperCase();
        logger.debug("Fct Ajouter utilisateur : " + nom);

        // Validation : vérifier que l'utilisateur n'existe pas déjà
        Optional<Utilisateurs> existant = administrationRepository.findByUtilisateur(nom);
        if (existant.isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec ce nom existe déjà : " + nom);
        }

        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setUtilisateur(nom);
        utilisateur.setActif(dto.getActif());
        utilisateur.setAdministration("N");
        utilisateur.setServeurRun("PRODUCTION");
        utilisateur.setMfaEnabled("N");
       utilisateur.setMfaSecret(""); // Le secret MFA est généré plus tard, pas à la création

        return administrationRepository.save(utilisateur);
    }
    /**
     * Modifier un utilisateur existant.
     *
     * La modification part de l'entité en base et n'applique que les champs
     * éditables du formulaire. Les champs techniques (serveurRun, administration,
     * mfaEnabled, mfaSecret) sont ainsi préservés : les écraser depuis le corps de
     * requête effacerait notamment le secret MFA de l'utilisateur.
     *
     * @param id  L'identifiant de l'utilisateur à modifier
     * @param dto Les champs éditables
     * @return L'utilisateur modifié
     * @throws IllegalArgumentException Si l'utilisateur n'existe pas ou si les données sont invalides
     */
    public Utilisateurs modifierUtilisateur(Long id, UtilisateurUpsertDto dto) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est requis pour la modification");
        }
        validerChampsObligatoires(dto);

        Utilisateurs existant = administrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aucun utilisateur trouvé avec l'ID : " + id));

        existant.setUtilisateur(dto.getUtilisateur().trim().toUpperCase());
        existant.setActif(dto.getActif());

        return administrationRepository.save(existant);
    }

    /**
     * Contrôles communs à la création et à la modification.
     */
    private void validerChampsObligatoires(UtilisateurUpsertDto dto) {
        if (dto == null || dto.getUtilisateur() == null || dto.getUtilisateur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire");
        }
        if (dto.getActif() == null || dto.getActif().trim().isEmpty()) {
            throw new IllegalArgumentException("Le champ Actif est obligatoire");
        }
    }

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<Utilisateurs> listerUtilisateurs() {
List<Utilisateurs> utilisateurs = new ArrayList<>();
for (Utilisateurs utilisateur : administrationRepository.findAllUtilisateurs()) {
           // affectationPortUtilisateur(utilisateur);
            utilisateurs.add(utilisateur);
        }

        return utilisateurs;
    }
/**
 * Affectation des informations de port d'un utilisateur.
 a partir de son type d'utilisateur.
 the libelle du port, la libelle de la r gion, et la liste des ports associ s
 * @param utilisateur L'utilisateur dont on a mettre jour les informations de port
 */

/**
 * Récupère les types d'utilisateurs disponibles
 * 
 * @return Liste des types d'utilisateurs (P=Port, R=Régional, T=Tous les
 *         Ports)
 */
    
    public List<ClasseOuiNon> listeActifOuiNon() {
        List<ClasseOuiNon> lTypeUtilisateur = new ArrayList<ClasseOuiNon>();
        lTypeUtilisateur.add(new ClasseOuiNon("O", "Oui"));
        lTypeUtilisateur.add(new ClasseOuiNon("N", "Non"));
        return lTypeUtilisateur;
    }


    
}

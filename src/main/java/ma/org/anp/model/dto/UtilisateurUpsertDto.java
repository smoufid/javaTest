package ma.org.anp.model.dto;

/**
 * Corps de requête pour la création et la modification d'un utilisateur.
 *
 * Ne porte que les champs réellement éditables depuis l'écran d'administration.
 * Les champs techniques (id, serveurRun, administration, mfaEnabled, mfaSecret)
 * sont volontairement absents : ils sont fixés par le serveur ou repris de
 * l'entité en base, et ne doivent jamais être pilotés par le client.
 */
public class UtilisateurUpsertDto {

    private String utilisateur;
    private String actif;

    public String getUtilisateur() { return utilisateur; }
    public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }

    public String getActif() { return actif; }
    public void setActif(String actif) { this.actif = actif; }
}

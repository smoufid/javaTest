package ma.org.anp.model.dto;

import ma.org.anp.model.entity.administration.Utilisateurs;

public class UtilisateurListDto {

    private Long id;
    private String utilisateur;
    private String serveurRun;
    private String actif;
    private String administration;
    private boolean mfaEnabled;

    public static UtilisateurListDto from(Utilisateurs u) {
        UtilisateurListDto dto = new UtilisateurListDto();
        dto.id = u.getId();
        dto.utilisateur = u.getUtilisateur();
        dto.serveurRun = u.getServeurRun();
        dto.actif = u.getActif();
        dto.administration = u.getAdministration();
        dto.mfaEnabled = u.getMfaEnabled();
        return dto;
    }

    public Long getId() { return id; }
    public String getUtilisateur() { return utilisateur; }
    public String getServeurRun() { return serveurRun; }
    public String getActif() { return actif; }
    public String getAdministration() { return administration; }
    public boolean isMfaEnabled() { return mfaEnabled; }
}

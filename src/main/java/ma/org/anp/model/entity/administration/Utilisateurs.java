package ma.org.anp.model.entity.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ma.org.anp.security.MfaSecretConverter;

@Entity
@Table(name = "TABLE_UTILISATEUR_FLUX_EDI", schema = "trfanp")
@NamedQueries({
        @NamedQuery(name = "Utilisateurs.findAll", query = "SELECT a FROM Utilisateurs a    order by a.utilisateur"),
        @NamedQuery(name = "Utilisateurs.getParUtilisateur", query = "SELECT a FROM Utilisateurs a WHERE a.utilisateur=:utilisateur and a.actif='O'")
})
public class Utilisateurs implements Serializable {

    private static final long serialVersionUID = -3163225040669213592L;

    public static final String GET_ALL = "Utilisateurs.findAll";
    public static final String GET_UTILISATEUR = "Utilisateurs.getParUtilisateur";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UTILISATEUR")
    private String utilisateur;

    @Column(name = "serveur_run")
    private String serveurRun;

    @Column(name = "actif")
    private String actif;
    @Column(name = "administration")
    private String administration;
    @Column(name = "mfa_enabled")
    private String mfaEnabled = "N";

    @JsonIgnore
    @Convert(converter = MfaSecretConverter.class)
    @Column(name = "mfa_secret")
    private String mfaSecret;

    public boolean getMfaEnabled() {
        return "O".equalsIgnoreCase(mfaEnabled);
    }

    public void setMfaEnabled(String mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public String getMfaSecret() {
        return mfaSecret;
    }

    public void setMfaSecret(String mfaSecret) {
        this.mfaSecret = mfaSecret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getServeurRun() {
        return serveurRun;
    }

    public void setServeurRun(String serveurRun) {
        this.serveurRun = serveurRun;
    }

    public String getActif() {
        return actif;
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public Utilisateurs() {
    }

    @Transient
    public Date getDateJour() {
        return new Date();
    }

    @Transient
    public String getCouleurEnv() {
        String ret = "green";
        if (serveurRun != null && !serveurRun.equalsIgnoreCase("production")) {
            ret = "red";
        }
        return ret;
    }

    @Transient
    public boolean getTestAdministration() {
        boolean ret = false;
        if (administration != null && administration.equalsIgnoreCase("o")) {
            ret = true;
        }
        return ret;
    }

}

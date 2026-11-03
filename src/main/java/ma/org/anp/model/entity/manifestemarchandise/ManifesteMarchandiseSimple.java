package ma.org.anp.model.entity.manifestemarchandise;

import java.io.Serializable;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;

@Entity
@Table(name = "edi_MANIFESTE_MARCHANDISE_NEW", schema = "trfanp")
@NamedQueries({
        @NamedQuery(name = "ManifesteMarchandiseSimple.findDateJour", query = "SELECT a FROM ManifesteMarchandiseSimple a where date(a.dateTraitement) =current_date "),
        @NamedQuery(name = "ManifesteMarchandiseSimple.findBynumeroEscale", query = "SELECT a FROM ManifesteMarchandiseSimple a where a.numeroEscale = :numeroEscale"),
        @NamedQuery(name = "ManifesteMarchandiseSimple.findByID", query = "SELECT a FROM ManifesteMarchandiseSimple a where a.id = :id"),
})
public class ManifesteMarchandiseSimple implements Serializable {
    private static final long serialVersionUID = -3163225040669213592L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom_fichier")
    private String nomFichier;
    @Column(name = "type_traitement")
    private String typeTraitement;
    @Column(name = "statut_traitement")
    private String statutTraitement;
    @Column(name = "date_traitement")
    private Date dateTraitement;
    @Column(name = "description_traitement")
    private String descriptionTraitement;
    @Column(name = "numero_message")
    private String numeroMessage;
    @Column(name = "date_reponse")
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dateReponse;
    @Column(name = "type_reponse")
    private String typeReponse;

    @Transient
    private String documentFile;
    @Column(name = "numero_escale")
    private long numeroEscale;

    @Column(name = "trafic")
    private String trafic;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getTypeTraitement() {
        return typeTraitement;
    }

    public void setTypeTraitement(String typeTraitement) {
        this.typeTraitement = typeTraitement;
    }

    public String getStatutTraitement() {
        return statutTraitement;
    }

    public void setStatutTraitement(String statutTraitement) {
        this.statutTraitement = statutTraitement;
    }

    public Date getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(Date dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public String getDescriptionTraitement() {
        return descriptionTraitement;
    }

    public void setDescriptionTraitement(String descriptionTraitement) {
        this.descriptionTraitement = descriptionTraitement;
    }

    public String getNumeroMessage() {
        return numeroMessage;
    }

    public void setNumeroMessage(String numeroMessage) {
        this.numeroMessage = numeroMessage;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getTypeReponse() {
        return typeReponse;
    }

    public void setTypeReponse(String typeReponse) {
        this.typeReponse = typeReponse;
    }

    public String getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(String documentFile) {
        this.documentFile = documentFile;
    }

    public long getNumeroEscale() {
        return numeroEscale;
    }

    public void setNumeroEscale(long numeroEscale) {
        this.numeroEscale = numeroEscale;
    }

    public String getTrafic() {
        return trafic;
    }

    public void setTrafic(String trafic) {
        this.trafic = trafic;
    }

    @Override
    public String toString() {
        return "ManifesteMarchandiseSimple [id=" + id + ", nomFichier=" + nomFichier + ", typeTraitement="
                + typeTraitement
                + ", statutTraitement=" + statutTraitement + ", dateTraitement=" + dateTraitement
                + ", descriptionTraitement=" + descriptionTraitement + ", numeroMessage=" + numeroMessage
                + ", dateReponse="
                + dateReponse + ", typeReponse=" + typeReponse + ", documentFile=" + documentFile + ", numeroEscale="
                + numeroEscale + ", trafic=" + trafic + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nomFichier == null) ? 0 : nomFichier.hashCode());
        result = prime * result + ((typeTraitement == null) ? 0 : typeTraitement.hashCode());
        result = prime * result + ((statutTraitement == null) ? 0 : statutTraitement.hashCode());
        result = prime * result + ((dateTraitement == null) ? 0 : dateTraitement.hashCode());
        result = prime * result + ((descriptionTraitement == null) ? 0 : descriptionTraitement.hashCode());
        result = prime * result + ((numeroMessage == null) ? 0 : numeroMessage.hashCode());
        result = prime * result + ((dateReponse == null) ? 0 : dateReponse.hashCode());
        result = prime * result + ((typeReponse == null) ? 0 : typeReponse.hashCode());
        result = prime * result + ((documentFile == null) ? 0 : documentFile.hashCode());
        result = prime * result + (int) (numeroEscale ^ (numeroEscale >>> 32));
        result = prime * result + ((trafic == null) ? 0 : trafic.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManifesteMarchandiseSimple other = (ManifesteMarchandiseSimple) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nomFichier == null) {
            if (other.nomFichier != null)
                return false;
        } else if (!nomFichier.equals(other.nomFichier))
            return false;
        if (typeTraitement == null) {
            if (other.typeTraitement != null)
                return false;
        } else if (!typeTraitement.equals(other.typeTraitement))
            return false;
        if (statutTraitement == null) {
            if (other.statutTraitement != null)
                return false;
        } else if (!statutTraitement.equals(other.statutTraitement))
            return false;
        if (dateTraitement == null) {
            if (other.dateTraitement != null)
                return false;
        } else if (!dateTraitement.equals(other.dateTraitement))
            return false;
        if (descriptionTraitement == null) {
            if (other.descriptionTraitement != null)
                return false;
        } else if (!descriptionTraitement.equals(other.descriptionTraitement))
            return false;
        if (numeroMessage == null) {
            if (other.numeroMessage != null)
                return false;
        } else if (!numeroMessage.equals(other.numeroMessage))
            return false;
        if (dateReponse == null) {
            if (other.dateReponse != null)
                return false;
        } else if (!dateReponse.equals(other.dateReponse))
            return false;
        if (typeReponse == null) {
            if (other.typeReponse != null)
                return false;
        } else if (!typeReponse.equals(other.typeReponse))
            return false;
        if (documentFile == null) {
            if (other.documentFile != null)
                return false;
        } else if (!documentFile.equals(other.documentFile))
            return false;
        if (numeroEscale != other.numeroEscale)
            return false;
        if (trafic == null) {
            if (other.trafic != null)
                return false;
        } else if (!trafic.equals(other.trafic))
            return false;
        return true;
    }

}

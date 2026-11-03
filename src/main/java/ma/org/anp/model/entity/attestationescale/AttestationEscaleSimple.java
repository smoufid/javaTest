package ma.org.anp.model.entity.attestationescale;
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
@Table(name = "edi_attestation_escale", schema = "trfanp")
@NamedQueries({
        @NamedQuery(name = "AttestationEscaleSimple.findDateJour", query = "SELECT a FROM AttestationEscaleSimple a where date(a.dateTraitement) =current_date "),
        @NamedQuery(name = "AttestationEscaleSimple.findByNumEscale", query = "SELECT a FROM AttestationEscaleSimple a where a.numeroEscale = :numeroEscale"),
        @NamedQuery(name = "AttestationEscaleSimple.findByID", query = "SELECT a FROM AttestationEscaleSimple a where a.id = :id"),
})
public class AttestationEscaleSimple implements Serializable {
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
    @Column(name = "numero_escale")
    private long numeroEscale;

    @Transient
    private String documentFile;

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

    public long getNumeroEscale() {
        return numeroEscale;
    }

    public void setNumeroEscale(long numeroEscale) {
        this.numeroEscale = numeroEscale;
    }

    public String getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(String documentFile) {
        this.documentFile = documentFile;
    }

    @Override
    public String toString() {
        return "AttestationEscaleSimple [id=" + id + ", nomFichier=" + nomFichier + ", typeTraitement=" + typeTraitement
                + ", statutTraitement=" + statutTraitement + ", dateTraitement=" + dateTraitement
                + ", descriptionTraitement=" + descriptionTraitement + ", numeroEscale=" + numeroEscale
                + ", documentFile=" + documentFile + "]";
    }


}

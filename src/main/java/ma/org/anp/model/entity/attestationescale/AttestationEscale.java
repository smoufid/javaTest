package ma.org.anp.model.entity.attestationescale;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

@Entity
@Table(name = "edi_attestation_escale", schema = "trfanp")
@NamedQueries({

        @NamedQuery(name = "AttestationEscale.findByID", query = "SELECT a FROM AttestationEscale a where a.id = :id"),
})
public class AttestationEscale implements Serializable {
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
    
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenu_fichier")
    private byte[] document;


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


    public byte[] getDocument() {
        return document;
    }


    public void setDocument(byte[] document) {
        this.document = document;
    }



}

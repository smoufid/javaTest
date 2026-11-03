package ma.org.anp.model.entity.mouvement;

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
@Table(name = "EDI_MOUVEMENTS", schema = "trfanp")
@NamedQueries({

        @NamedQuery(name = "Mouvements.findByID", query = "SELECT a FROM Mouvements a where a.id = :id"),
})
public class Mouvements implements Serializable {
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
    @Column(name = "numero_avis")
    private String numeroAvis;
    
    
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


    public String getNumeroAvis() {
        return numeroAvis;
    }


    public void setNumeroAvis(String numeroAvis) {
        this.numeroAvis = numeroAvis;
    }


    public byte[] getDocument() {
        return document;
    }


    public void setDocument(byte[] document) {
        this.document = document;
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
        result = prime * result + ((numeroAvis == null) ? 0 : numeroAvis.hashCode());
        result = prime * result + Arrays.hashCode(document);
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
        Mouvements other = (Mouvements) obj;
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
        if (numeroAvis == null) {
            if (other.numeroAvis != null)
                return false;
        } else if (!numeroAvis.equals(other.numeroAvis))
            return false;
        if (!Arrays.equals(document, other.document))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Mouvements [id=" + id + ", nomFichier=" + nomFichier + ", typeTraitement=" + typeTraitement
                + ", statutTraitement=" + statutTraitement + ", dateTraitement=" + dateTraitement
                + ", descriptionTraitement=" + descriptionTraitement + ", numeroAvis=" + numeroAvis + ", document="
                + Arrays.toString(document) + "]";
    }

  

}

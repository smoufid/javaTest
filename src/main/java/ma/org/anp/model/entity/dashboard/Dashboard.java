package ma.org.anp.model.entity.dashboard;

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
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "EDI_FLUX_ECHANGE", schema = "trfanp")
@NamedQueries({
        @NamedQuery(name = "Dashboard.findDateJour", query = "SELECT a FROM Dashboard a where date(a.dateTraitement) =current_date "),
        @NamedQuery(name = "Dashboard.findByDate", query = "SELECT a FROM Dashboard a where date(a.dateTraitement) =:date "),
        @NamedQuery(name = "Dashboard.getListePeriodeAvis", query = "SELECT a FROM Dashboard a WHERE cast(a.dateTraitement as date) between :date1 and :date2 and a.typeTraitement ='IFTMAN' and a.dateMessage is not null    "),
        @NamedQuery(name = "Dashboard.getListePeriodeDAP", query = "SELECT a FROM Dashboard a WHERE cast(a.dateTraitement as date) between :date1 and :date2 and a.typeTraitement ='IFTMBP' and a.dateMessage is not null    "),
        @NamedQuery(name = "Dashboard.getListePeriodeManifeste", query = "SELECT a FROM Dashboard a WHERE cast(a.dateTraitement as date) between :date1 and :date2 and a.typeTraitement ='MANIFESTEM' and a.dateMessage is not null    ")

})
public class Dashboard implements Serializable {

    private static final long serialVersionUID = -3163225040669213592L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom_fichier")
    private String nomFichier;
    @Column(name = "type_traitement")
    private String typeTraitement;
    @Column(name = "date_traitement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;
    @Column(name = "description_traitement")
    private String descriptionTraitement;
    @Column(name = "date_message")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMessage;
    @Transient
    private Date dateTraitementFormat;
    @Transient
    private long delais;

    public long getDelais() {
        return delais;
    }

    public void setDelais(long delais) {
        this.delais = delais;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getDateTraitementFormat() {
        return dateTraitementFormat;
    }

    public void setDateTraitementFormat(Date dateTraitementFormat) {
        this.dateTraitementFormat = dateTraitementFormat;
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

    public Date getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(Date dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public String getDescriptionTraitement() {
        return descriptionTraitement.trim();
    }

    public void setDescriptionTraitement(String descriptionTraitement) {
        this.descriptionTraitement = descriptionTraitement;
    }

    @Override
    public String toString() {
        return "Dashboard [id=" + id + ", nomFichier=" + nomFichier + ", typeTraitement=" + typeTraitement
                + ", dateTraitement=" + dateTraitement + ", descriptionTraitement=" + descriptionTraitement
                + ", dateMessage=" + dateMessage + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nomFichier == null) ? 0 : nomFichier.hashCode());
        result = prime * result + ((typeTraitement == null) ? 0 : typeTraitement.hashCode());
        result = prime * result + ((dateTraitement == null) ? 0 : dateTraitement.hashCode());
        result = prime * result + ((descriptionTraitement == null) ? 0 : descriptionTraitement.hashCode());
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
        Dashboard other = (Dashboard) obj;
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
        return true;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

}

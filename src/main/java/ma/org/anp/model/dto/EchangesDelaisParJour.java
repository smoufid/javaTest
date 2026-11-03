package ma.org.anp.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EchangesDelaisParJour implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Date de l'échange au format jj-MM-yyyy (ex. "01-06-2026"). */
    private String dateEchange;

    /** Répartition par tranche de délai : toujours 3 éléments. */
    private List<EchangesTranches> tranche = new ArrayList<>();

    public EchangesDelaisParJour() {
    }

    public EchangesDelaisParJour(String dateEchange) {
        this.dateEchange = dateEchange;
    }

    public String getDateEchange() {
        return dateEchange;
    }

    public void setDateEchange(String dateEchange) {
        this.dateEchange = dateEchange;
    }

    public List<EchangesTranches> getTranche() {
        return tranche;
    }

    public void setTranche(List<EchangesTranches> tranche) {
        this.tranche = tranche;
    }

    /** Ajoute une tranche à la liste (évite getTranche().add(...) côté service). */
    public void addTranche(String libelle, long nombreEchange) {
        this.tranche.add(new EchangesTranches(libelle, nombreEchange));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateEchange == null) ? 0 : dateEchange.hashCode());
        result = prime * result + ((tranche == null) ? 0 : tranche.hashCode());
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
        EchangesDelaisParJour other = (EchangesDelaisParJour) obj;
        if (dateEchange == null) {
            if (other.dateEchange != null)
                return false;
        } else if (!dateEchange.equals(other.dateEchange))
            return false;
        if (tranche == null) {
            if (other.tranche != null)
                return false;
        } else if (!tranche.equals(other.tranche))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EchangesDelaisParJour [dateEchange=" + dateEchange + ", tranche=" + tranche + "]";
    }
}
package ma.org.anp.model.dto;

import java.io.Serializable;

public class EchangesTranches implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tranche;
    private long nombreEchange;

    public EchangesTranches() {
    }

    public EchangesTranches(String tranche, long nombreEchange) {
        this.tranche = tranche;
        this.nombreEchange = nombreEchange;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }

    public long getNombreEchange() {
        return nombreEchange;
    }

    public void setNombreEchange(long nombreEchange) {
        this.nombreEchange = nombreEchange;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tranche == null) ? 0 : tranche.hashCode());
        result = prime * result + (int) (nombreEchange ^ (nombreEchange >>> 32));
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
        EchangesTranches other = (EchangesTranches) obj;
        if (tranche == null) {
            if (other.tranche != null)
                return false;
        } else if (!tranche.equals(other.tranche))
            return false;
        return nombreEchange == other.nombreEchange;
    }

    @Override
    public String toString() {
        return "EchangesDelais [tranche=" + tranche + ", nombreEchange=" + nombreEchange + "]";
    }
}
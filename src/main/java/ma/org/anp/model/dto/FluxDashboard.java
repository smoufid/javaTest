package ma.org.anp.model.dto;

import java.io.Serializable;

public class FluxDashboard implements Serializable {
private String typeFichier;
private long nombreFichier;
private long delaiMoyen;
private long delaiMax;
private long delaiMin;
public String getTypeFichier() {
    return typeFichier;
}
public void setTypeFichier(String typeFichier) {
    this.typeFichier = typeFichier;
}
public long getNombreFichier() {
    return nombreFichier;
}
public void setNombreFichier(long nombreFichier) {
    this.nombreFichier = nombreFichier;
}
public long getDelaiMoyen() {
    return delaiMoyen;
}
public void setDelaiMoyen(long delaiMoyen) {
    this.delaiMoyen = delaiMoyen;
}
public long getDelaiMax() {
    return delaiMax;
}
public void setDelaiMax(long delaiMax) {
    this.delaiMax = delaiMax;
}
public long getDelaiMin() {
    return delaiMin;
}
public void setDelaiMin(long delaiMin) {
    this.delaiMin = delaiMin;
}
@Override
public String toString() {
    return "FluxDashboard [typeFichier=" + typeFichier + ", nombreFichier=" + nombreFichier + ", delaiMoyen="
            + delaiMoyen + ", delaiMax=" + delaiMax + ", delaiMin=" + delaiMin + "]";
}
  

}

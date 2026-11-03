package ma.org.anp.model.dto;

public class ClasseOuiNon {
    private String code;
    private String libelle;
    public ClasseOuiNon() {
        
    }
    public ClasseOuiNon(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


}

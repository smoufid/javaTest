package ma.org.anp.model.dto;

import java.util.ArrayList;
import java.util.List;

import ma.org.anp.model.entity.administration.Utilisateurs;

 

public class UserInfoDTO {
    
    private Long id;
    private String utilisateur;
    private String serveurRun;
   private String administration;
    private boolean testAdministration;
   
    

  
    public UserInfoDTO() {
    }
    
    public UserInfoDTO(Utilisateurs utilisateur) {
        this.id = utilisateur.getId();
        this.utilisateur = utilisateur.getUtilisateur();
        /* this.nomUtilisateur = utilisateur.getNomUtilisateur(); */
       
        this.serveurRun = utilisateur.getServeurRun();
          
        this.setAdministration(utilisateur.getAdministration());
        this.setTestAdministration(utilisateur.getTestAdministration());
         

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
    
   
    
   
    
    

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    public boolean isTestAdministration() {
        return testAdministration;
    }

    public void setTestAdministration(boolean testAdministration) {
        this.testAdministration = testAdministration;
    }

    

     

    
}

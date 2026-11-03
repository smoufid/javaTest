package ma.org.anp.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    
    @NotNull(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 1, message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;
    
    @NotNull(message = "Le mot de passe est obligatoire")
    @Size(min = 1, message = "Le mot de passe ne peut pas être vide")
    private String password;
    
    public LoginRequest() {
    }
    
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username.toUpperCase();
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

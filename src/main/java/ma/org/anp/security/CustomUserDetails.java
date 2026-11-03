package ma.org.anp.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ma.org.anp.model.entity.administration.Utilisateurs;

public class CustomUserDetails implements UserDetails {
    
    private Long userId;
    private String username;
    private String password;
  
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetails(Long userId, String username, String password, 
                           
                           Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        
        this.authorities = authorities;
    }
    
    public static CustomUserDetails build(Utilisateurs utilisateur, String password) {
        String role = "O".equalsIgnoreCase(utilisateur.getAdministration()) ? "ADMIN" : "USER";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return new CustomUserDetails(
            utilisateur.getId(),
            utilisateur.getUtilisateur(),
            password,
            Collections.singletonList(authority)
        );
    }
    
    public Long getUserId() {
        return userId;
    }
    
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}

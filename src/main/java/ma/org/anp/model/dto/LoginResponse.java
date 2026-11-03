package ma.org.anp.model.dto;

public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private UserInfoDTO user;
     private boolean mfaRequired = false;
    private String tempToken;
    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public UserInfoDTO getUser() {
        return user;
    }

    public LoginResponse() {
    }
    
    public LoginResponse(String token, UserInfoDTO user) {
        this.token = token;
        this.user = user;
    }
    public LoginResponse(String tempToken, boolean mfaRequired) {
        this.tempToken = tempToken;
        this.mfaRequired = mfaRequired;
    }
    public LoginResponse(String token ) {
        this.token = token;
      
    }
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public boolean isMfaRequired() {
        return mfaRequired;
    }

    public void setMfaRequired(boolean mfaRequired) {
        this.mfaRequired = mfaRequired;
    }

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }
    

}

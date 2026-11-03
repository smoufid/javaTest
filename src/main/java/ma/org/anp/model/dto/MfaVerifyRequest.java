// MfaVerifyRequest.java
package ma.org.anp.model.dto;

public class MfaVerifyRequest {
    private String tempToken;
    private String code;

    public MfaVerifyRequest() {}

    public String getTempToken() { return tempToken; }
    public void setTempToken(String tempToken) { this.tempToken = tempToken; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
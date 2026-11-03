package ma.org.anp.model.dto;

public class MfaSetupResponse {
    private String otpAuthUrl;

    public MfaSetupResponse(String otpAuthUrl) {
        this.otpAuthUrl = otpAuthUrl;
    }

    public String getOtpAuthUrl() { return otpAuthUrl; }
    public void setOtpAuthUrl(String otpAuthUrl) { this.otpAuthUrl = otpAuthUrl; }
}
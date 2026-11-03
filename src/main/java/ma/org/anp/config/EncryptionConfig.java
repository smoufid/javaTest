package ma.org.anp.config;

import ma.org.anp.security.MfaSecretConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class EncryptionConfig {

    @Value("${mfa.encryption.key}")
    private String mfaEncryptionKey;

    @PostConstruct
    public void initMfaEncryption() {
        MfaSecretConverter.setEncryptionKey(mfaEncryptionKey);
    }
}

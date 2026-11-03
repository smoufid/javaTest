package ma.org.anp.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Chiffre/déchiffre la colonne mfa_secret en AES-256/GCM.
 * La clé est injectée au démarrage par EncryptionConfig via setEncryptionKey().
 * Format stocké en base : Base64( IV[12] || CipherText || AuthTag[16] )
 */
@Converter
public class MfaSecretConverter implements AttributeConverter<String, String> {

    private static final int GCM_IV_LENGTH  = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private static volatile String encryptionKeyBase64;

    public static void setEncryptionKey(String keyBase64) {
        encryptionKeyBase64 = keyBase64;
    }

    @Override
    public String convertToDatabaseColumn(String plainText) {
        if (plainText == null) return null;
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[GCM_IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, GCM_IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, GCM_IV_LENGTH, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement du secret MFA", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            byte[] combined = Base64.getDecoder().decode(dbData);
            byte[] iv        = Arrays.copyOfRange(combined, 0, GCM_IV_LENGTH);
            byte[] encrypted = Arrays.copyOfRange(combined, GCM_IV_LENGTH, combined.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, getKey(), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Données non encore chiffrées (migration) : retourne la valeur brute
            return dbData;
        }
    }

    private SecretKey getKey() {
        if (encryptionKeyBase64 == null || encryptionKeyBase64.trim().isEmpty()) {
            throw new IllegalStateException(
                "Clé de chiffrement MFA non initialisée. Définissez la variable d'environnement MFA_ENCRYPTION_KEY.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(encryptionKeyBase64);
        return new SecretKeySpec(keyBytes, "AES");
    }
}

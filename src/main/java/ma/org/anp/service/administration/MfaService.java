package ma.org.anp.service.administration;

import ma.org.anp.model.entity.administration.Utilisateurs;
import ma.org.anp.repository.AuthentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class MfaService {
    private static final Logger logger = LoggerFactory.getLogger(MfaService.class);
    @Value("${mfa.issuer:ECHANGESPORTNET}")
    private String issuer;

    @Autowired
    private AuthentificationRepository authentificationRepository;

    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    // ─── Décodage Base32 natif ───────────────────────────────────────────────

    private byte[] base32Decode(String input) {
        input = input.toUpperCase().replaceAll("[^A-Z2-7]", "");
        int buffer = 0;
        int bitsLeft = 0;
        byte[] result = new byte[input.length() * 5 / 8];
        int index = 0;
        for (char c : input.toCharArray()) {
            int val = BASE32_CHARS.indexOf(c);
            if (val < 0)
                continue;
            buffer <<= 5;
            buffer |= val;
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                result[index++] = (byte) (buffer >> (bitsLeft - 8));
                bitsLeft -= 8;
            }
        }
        return result;
    }

    // ─── Génération du secret ────────────────────────────────────────────────

    public String generateSecret() {
        SecureRandom random = new SecureRandom();
        StringBuilder secret = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            secret.append(BASE32_CHARS.charAt(random.nextInt(32)));
        }
        return secret.toString();
    }

    // ─── URI otpauth (le frontend génère le QR code) ─────────────────────────

    public String generateOtpAuthUrl(String username, String secret) {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                issuer, username, secret, issuer);
    }

    // ─── Code TOTP pour un timeIndex donné ──────────────────────────────────

    private String getCode(String secret, long timeIndex)
            throws NoSuchAlgorithmException, InvalidKeyException {

        byte[] secretBytes = base32Decode(secret);

        byte[] timeBytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            timeBytes[i] = (byte) (timeIndex & 0xFF);
            timeIndex >>= 8;
        }

        SecretKeySpec signKey = new SecretKeySpec(secretBytes, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);

        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1_000_000;

        return String.format("%06d", truncatedHash);
    }

    // ─── Validation du code TOTP ─────────────────────────────────────────────

    public boolean verifyCode(String secret, String code) {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;

        for (int i = -1; i <= 1; i++) {
            try {
                String generated = getCode(secret, timeIndex + i);
                if (generated.equals(code)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la vérification du code MFA", e);
                return false;
            }
        }
        return false;
    }

    // ─── Activation MFA ──────────────────────────────────────────────────────

    public void enableMfa(String username, String secret) {
        Utilisateurs user = authentificationRepository
                .findByUtilisateur(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setMfaSecret(secret);
        user.setMfaEnabled("O");
        authentificationRepository.save(user);
    }

    // ─── Désactivation MFA ───────────────────────────────────────────────────

    public void disableMfa(String username) {
        Utilisateurs user = authentificationRepository
                .findByUtilisateur(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setMfaSecret(null);
        user.setMfaEnabled("N");
        authentificationRepository.save(user);
    }
}
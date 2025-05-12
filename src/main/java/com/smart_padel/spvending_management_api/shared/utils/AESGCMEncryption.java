package com.smart_padel.spvending_management_api.shared.utils;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class AESGCMEncryption {
    private AESGCMEncryption() {
        throw new IllegalStateException("Utility class");
    }
    private static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;   // 96 bits recomendado para GCM
    private static final int SALT_LENGTH_BYTE = 16; // 128 bits recomendado para PBKDF2
    private static final int KEY_LENGTH_BIT = 256;
    private static final int ITERATION_COUNT = 65536;

    public static String encrypt(String plainText, String aeSecretKey) throws GeneralSecurityException {
        byte[] salt = generateRandomBytes(SALT_LENGTH_BYTE);
        byte[] iv = generateRandomBytes(IV_LENGTH_BYTE);

        if (plainText==null || plainText.isEmpty()) {
            throw new IllegalArgumentException("The text to encrypt must be defined.");
        }

        if (aeSecretKey == null || aeSecretKey.length() < 12) {
            throw new IllegalArgumentException("The encryption secret key must be defined and at least 12 characters long.");
        }

        SecretKeySpec keySpec = deriveKey(aeSecretKey, salt);

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[salt.length + iv.length + encryptedBytes.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, salt.length + iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String encryptedText, String aeSecretKey) throws GeneralSecurityException {
        if (encryptedText == null) {
            throw new IllegalArgumentException("Encrypted text is too short or corrupted.");
        }

        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        if (aeSecretKey == null || aeSecretKey.length() < 12) {
            throw new IllegalArgumentException("The encryption secret key must be defined and at least 12 characters long.");
        }

        if (decoded.length < SALT_LENGTH_BYTE + IV_LENGTH_BYTE) {
            throw new IllegalArgumentException("Encrypted text is too short or corrupted.");
        }

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        byte[] iv = new byte[IV_LENGTH_BYTE];
        byte[] cipherText = new byte[decoded.length - SALT_LENGTH_BYTE - IV_LENGTH_BYTE];

        System.arraycopy(decoded, 0, salt, 0, SALT_LENGTH_BYTE);
        System.arraycopy(decoded, SALT_LENGTH_BYTE, iv, 0, IV_LENGTH_BYTE);
        System.arraycopy(decoded, SALT_LENGTH_BYTE + IV_LENGTH_BYTE, cipherText, 0, cipherText.length);

        SecretKeySpec keySpec = deriveKey(aeSecretKey, salt);

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec deriveKey(String secret, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH_BIT);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }
}

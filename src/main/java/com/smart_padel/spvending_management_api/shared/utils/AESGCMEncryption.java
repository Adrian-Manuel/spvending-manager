package com.smart_padel.spvending_management_api.shared.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class AESGCMEncryption {

    private static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12; // 96 bits recomendado para GCM
    private static final int SALT_LENGTH_BYTE = 16; // 128 bits recomendado para PBKDF2

    @Value("${app.AESecret_key}")
    private String aeSecretKey;

    public String encrypt(String plainText) throws Exception {
        // 1. Generar salt y IV aleatorios
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        random.nextBytes(iv);

        // 2. Derivar la clave con PBKDF2
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(aeSecretKey.toCharArray(), salt, 65536, 256);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // 3. Configurar GCM
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        // 4. Cifrar
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // 5. Concatenar salt + IV + cifrado y codificar en Base64
        byte[] combined = new byte[salt.length + iv.length + encryptedBytes.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, salt.length + iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedText);

        // 1. Extraer salt, IV y datos cifrados
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        byte[] iv = new byte[IV_LENGTH_BYTE];
        byte[] cipherText = new byte[decoded.length - SALT_LENGTH_BYTE - IV_LENGTH_BYTE];

        System.arraycopy(decoded, 0, salt, 0, SALT_LENGTH_BYTE);
        System.arraycopy(decoded, SALT_LENGTH_BYTE, iv, 0, IV_LENGTH_BYTE);
        System.arraycopy(decoded, SALT_LENGTH_BYTE + IV_LENGTH_BYTE, cipherText, 0, cipherText.length);

        // 2. Derivar la clave con PBKDF2
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(aeSecretKey.toCharArray(), salt, 65536, 256);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // 3. Configurar GCM
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        // 4. Descifrar
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

package com.smart_padel.spvending_management_api.shared.utils;
import org.junit.jupiter.api.Test;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class AESGCMEncryptionTest {

    private static final String SECRET_KEY = "ThisIsAStrongKey1234567890";
    private static final String PLAIN_TEXT = "This is some sensitive data!";

    @Test
    void encrypt_NullKey_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                AESGCMEncryption.encrypt(PLAIN_TEXT, null));
        assertEquals("The encryption secret key must be defined and at least 12 characters long.", exception.getMessage());
    }

    @Test
    void decrypt_EmptyEncryptedText_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                AESGCMEncryption.decrypt("", SECRET_KEY));
        assertEquals("Encrypted text is too short or corrupted.", exception.getMessage());
    }

    @Test
    void decrypt_InvalidBase64EncryptedText_ThrowsIllegalArgumentException() {
        String invalidBase64 = "InvalidBase64Text";
        assertThrows(IllegalArgumentException.class, () ->
                AESGCMEncryption.decrypt(invalidBase64, SECRET_KEY));
    }

    @Test
    void encryptAndDecrypt_EmptyPlainText_ReturnsEmptyString(){
        assertThrows(IllegalArgumentException.class, () ->
                AESGCMEncryption.encrypt("", SECRET_KEY));
        assertThrows(IllegalArgumentException.class, () ->
                AESGCMEncryption.decrypt("", SECRET_KEY));
    }

    @Test
    void encryptAndDecrypt_LargePlainText_ReturnsOriginalPlainText() throws GeneralSecurityException {
        String largePlainText = "A".repeat(10000);
        String encryptedText = AESGCMEncryption.encrypt(largePlainText, SECRET_KEY);
        String decryptedText = AESGCMEncryption.decrypt(encryptedText, SECRET_KEY);
        assertEquals(largePlainText, decryptedText);
    }
}
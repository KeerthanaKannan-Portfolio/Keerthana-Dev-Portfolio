package com.banking.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionUtil {

   
    private static final String SECRET_KEY = "BankingApp123456";
    private static final String ALGORITHM  = "AES";

    
    public static String encrypt(String plainText) {
        try {
            SecretKey key    = new SecretKeySpec(
                    SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher    = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(
                    "[ERROR] Encryption failed: " + e.getMessage());
        }
    }

   
    public static String decrypt(String encryptedText) {
        try {
            SecretKey key   = new SecretKeySpec(
                    SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher   = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded  = Base64.getDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(
                    "[ERROR] Decryption failed: " + e.getMessage());
        }
    }
}
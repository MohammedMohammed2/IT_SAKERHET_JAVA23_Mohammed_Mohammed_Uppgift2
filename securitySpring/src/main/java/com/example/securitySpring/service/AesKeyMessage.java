package com.example.securitySpring.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class AesKeyMessage {
    private static final String secret_Key = "kYbIsTw1g6Gi63Ec0gnx3z==";
    public static String AESCrypt(String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(secret_Key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public static String AESDecrypt(String message) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(secret_Key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(decryptedBytes);
    }
}

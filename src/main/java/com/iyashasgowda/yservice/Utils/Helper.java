package com.iyashasgowda.yservice.Utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class Helper {

    public String encrypt(String password) {
        try {
            String key = "viva-last-vegan";
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());

            return new String(encrypted);
        } catch (Exception e) {
            return new String(Base64.encodeBase64(password.getBytes()));
        }
    }

    public String decrypt(String password) {
        try {
            String key = "viva-last-vegan";
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decrypted = cipher.doFinal(password.getBytes());

            return new String(decrypted);
        } catch (Exception e) {
            return new String(Base64.decodeBase64(password.getBytes()));
        }
    }
}

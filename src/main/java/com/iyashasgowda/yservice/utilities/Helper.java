package com.iyashasgowda.yservice.utilities;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.Random;

import static com.iyashasgowda.yservice.utilities.Constants.A2Z;

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

    public String generateUsername(String name) {
        if (name.length() > 16) name = name.substring(0, 11);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16 - name.length(); i++)
            sb.append(A2Z.charAt(new Random().nextInt(A2Z.length())));

        return name.concat(sb.toString());
    }

    public String getFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) return null;

        String[] fileNameParts = filename.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }

    public MediaType getMediaType(MultipartFile file) {
        String extension = getFileExtension(file);
        if (Arrays.asList(Constants.VIDEO_EXT).contains(extension)) return MediaType.VIDEO;
        if (Arrays.asList(Constants.IMAGE_EXT).contains(extension)) return MediaType.IMAGE;
        return MediaType.INVALID;
    }

    public long getMediaSize(MultipartFile file) {
        return file.getSize();
    }
}

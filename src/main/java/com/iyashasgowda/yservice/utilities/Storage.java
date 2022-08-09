package com.iyashasgowda.yservice.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Component
public class Storage {
    private final Path VIDEO_STORAGE_LOCATION;
    private final Path IMAGE_STORAGE_LOCATION;

    @Autowired
    private Helper helper;

    @Autowired
    public Storage(Environment env) {
        this.VIDEO_STORAGE_LOCATION = Paths
                .get(env.getProperty("app.file.video-upload-dir", "./uploads/videos"))
                .toAbsolutePath()
                .normalize();

        this.IMAGE_STORAGE_LOCATION = Paths
                .get(env.getProperty("app.file.image-upload-dir", "./uploads/images"))
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.VIDEO_STORAGE_LOCATION);
            Files.createDirectories(this.IMAGE_STORAGE_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String storeVideo(MultipartFile file) {
        String identifier = new Date().getTime() + "." + helper.getFileExtension(file);

        try {
            Path targetLocation = VIDEO_STORAGE_LOCATION.resolve(identifier);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return identifier;
        } catch (IOException ex) {
            return null;
        }
    }

    public String storeImage(MultipartFile file) {
        String identifier = new Date().getTime() + "." + helper.getFileExtension(file);

        try {
            Path targetLocation = IMAGE_STORAGE_LOCATION.resolve(identifier);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return identifier;
        } catch (IOException ex) {
            return null;
        }
    }
}

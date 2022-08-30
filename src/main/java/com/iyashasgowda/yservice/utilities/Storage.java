package com.iyashasgowda.yservice.utilities;

import com.iyashasgowda.yservice.entities.Media;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.iyashasgowda.yservice.utilities.Constants.MEDIA_PATH;

@Component
public class Storage {
    private final Path VIDEO_STORAGE_LOCATION;
    private final Path THUMB_STORAGE_LOCATION;
    private final Path IMAGE_STORAGE_LOCATION;
    private final Path DELETED_MEDIA_LOCATION;

    @Autowired
    private Helper helper;

    @Autowired
    public Storage(Environment env) {
        this.VIDEO_STORAGE_LOCATION = Paths.get(env.getProperty("app.file.video-upload-dir", "./uploads/videos")).toAbsolutePath().normalize();

        this.THUMB_STORAGE_LOCATION = Paths.get(env.getProperty("app.file.thumb-upload-dir", "./uploads/videos/thumbs")).toAbsolutePath().normalize();

        this.IMAGE_STORAGE_LOCATION = Paths.get(env.getProperty("app.file.image-upload-dir", "./uploads/images")).toAbsolutePath().normalize();

        this.DELETED_MEDIA_LOCATION = Paths.get(env.getProperty("app.file.deleted-upload-dir", "./uploads/deleted")).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.VIDEO_STORAGE_LOCATION);
            Files.createDirectories(this.THUMB_STORAGE_LOCATION);
            Files.createDirectories(this.IMAGE_STORAGE_LOCATION);
            Files.createDirectories(this.DELETED_MEDIA_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeVideo(Media media, MultipartFile file) {
        long identifier = System.currentTimeMillis();
        String filename = identifier + "." + helper.getFileExtension(file);

        try {
            Path targetLocation = VIDEO_STORAGE_LOCATION.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            /* Setting video file name */
            media.setFilename(filename);

            /* Setting video url */
            media.setUrl(MEDIA_PATH + "/videos/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Generating thumbnail for video */
        generateThumbnail(media, identifier);
    }

    public void generateThumbnail(Media media, long identifier) {
        String videoPath = VIDEO_STORAGE_LOCATION + "\\" + media.getFilename();
        String filename = "thumbnail_" + identifier + ".jpeg";
        String thumbPath = THUMB_STORAGE_LOCATION + "\\" + filename;
        try {
            BufferedImage bi = AWTUtil.toBufferedImage(FrameGrab.getFrameFromFile(new File(videoPath), 1));
            ImageIO.write(Scalr.resize(bi, 480), "jpeg", new File(thumbPath));

            /* Setting video height & width */
            media.setWidth(bi.getWidth());
            media.setHeight(bi.getHeight());

            /* Setting thumbnail url */
            media.setThumbnail(MEDIA_PATH + "/videos/thumbs/" + filename);
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
    }

    public void storeImage(Media media, MultipartFile file) {
        String identifier = System.currentTimeMillis() + "." + helper.getFileExtension(file);

        try {
            Path targetLocation = IMAGE_STORAGE_LOCATION.resolve(identifier);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            /* Setting image file name */
            media.setFilename(identifier);

            /* Setting image url */
            media.setUrl(MEDIA_PATH + "/images/" + identifier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeMedia(String identifier, MediaType type) {
        try {
            Path source_location;
            if (type == MediaType.VIDEO) source_location = VIDEO_STORAGE_LOCATION.resolve(identifier);
            else if (type == MediaType.IMAGE) source_location = IMAGE_STORAGE_LOCATION.resolve(identifier);
            else return false;

            Path destination_location = DELETED_MEDIA_LOCATION.resolve("deleted_".concat(identifier));
            Files.move(source_location, destination_location, StandardCopyOption.REPLACE_EXISTING);

            try {
                if (type == MediaType.VIDEO) {
                    String thumbnail = "thumbnail_" + identifier.split("\\.")[0] + ".jpeg";

                    File thumb = THUMB_STORAGE_LOCATION.resolve(thumbnail).toAbsolutePath().toFile();
                    if (thumb.exists())
                        thumb.delete();
                }
            } catch (Exception t) {
                t.printStackTrace();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

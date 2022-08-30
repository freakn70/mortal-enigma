package com.iyashasgowda.yservice.utilities;

import com.iyashasgowda.yservice.entities.Media;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static com.iyashasgowda.yservice.utilities.Constants.A2Z;

@Component
public class Helper {

    public String generateUsername(String name) {
        if (name.length() > 16) name = name.substring(0, 11);

        StringBuilder sb = new StringBuilder();
        for (int i = name.length(); i < 16; i++)
            sb.append(A2Z.charAt(new Random().nextInt(A2Z.length())));

        return name.concat(sb.toString());
    }

    public String getFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) return null;

        String[] fileNameParts = filename.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }

    public void setMediaType(Media media, MultipartFile file) {
        String extension = getFileExtension(file);
        if (Arrays.asList(Constants.VIDEO_EXT).contains(extension)) media.setType(MediaType.VIDEO);
        else if (Arrays.asList(Constants.IMAGE_EXT).contains(extension)) media.setType(MediaType.IMAGE);
        else media.setType(MediaType.INVALID);
    }

    public void setVideoMetadata(Media media, MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {

            /* Setting media duration */
            try {
                File video = new File(filename);
                FileCopyUtils.copy(file.getBytes(), video);

                media.setDuration(new MultimediaObject(video).getInfo().getDuration() / 1000);
                video.delete();
            } catch (IOException | EncoderException e) {
                e.printStackTrace();
            }

            /* Setting media title */
            if (filename.indexOf(".") > 0 && getFileExtension(file) != null)
                media.setTitle(filename.substring(0, filename.lastIndexOf(".")));
            else
                media.setTitle(media.getFilename());

            /* Setting media file format */
            media.setFormat(getFileExtension(file));

            /* Setting media size */
            media.setSize(file.getSize());
        }
    }

    public void setImageMetadata(Media media, MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            File image = new File(filename);

            /* Setting media height & width *****************************/
            try {
                BufferedImage bi = ImageIO.read(image);

                media.setWidth(bi.getWidth());
                media.setHeight(bi.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* Setting media title ***************************************/
            if (filename.indexOf(".") > 0 && getFileExtension(file) != null)
                media.setTitle(filename.substring(0, filename.lastIndexOf(".")));
            else
                media.setTitle(media.getFilename());

            /* Setting media file format *********************************/
            media.setFormat(getFileExtension(file));

            /* Setting media size *****************************************/
            media.setSize(file.getSize());
        }
    }
}
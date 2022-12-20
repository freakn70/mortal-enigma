package com.mortalenigma.utilities;

import com.mortalenigma.entities.Media;
import com.mortalenigma.utilities.Constants.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import static com.mortalenigma.utilities.Constants.*;

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
            setMediaTitle(media, file, filename);

            /* Setting media file format */
            media.setFormat(getFileExtension(file));

            /* Setting media size */
            media.setSize(file.getSize());
        }
    }

    public void setImageMetadata(Media media, MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {

            /* Setting media height & width */
            try {
                BufferedImage bi = ImageIO.read(new URL(media.getUrl()));

                media.setWidth(bi.getWidth());
                media.setHeight(bi.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* Setting media title */
            setMediaTitle(media, file, filename);

            /* Setting media file format */
            media.setFormat(getFileExtension(file));

            /* Setting media size */
            media.setSize(file.getSize());
        }
    }

    public String generateOtp(int length, boolean useAlphabets, boolean useChars) {
        String values = NUMBERS;
        if(useAlphabets) values = String.format("%s%s%s", values, UPPERCASE_LETTERS, LOWERCASE_LETTERS);
        if(useChars) values = String.format("%s%s", values, SPECIAL_CHARS);

        Random random = new Random();
        String OTP = "";
        for (int i = 0; i < length; i++) OTP = String.format("%s%s", OTP, values.charAt(random.nextInt(values.length())));
        return OTP;
    }

    private void setMediaTitle(Media media, MultipartFile file, String filename) {
        if (media.getTitle() == null) {
            if (filename.indexOf(".") > 0 && getFileExtension(file) != null)
                media.setTitle(filename.substring(0, filename.lastIndexOf(".")));
            else
                media.setTitle(media.getFilename());
        }
    }
}
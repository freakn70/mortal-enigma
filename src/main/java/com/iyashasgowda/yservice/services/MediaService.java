package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.repositories.MediaRepository;
import com.iyashasgowda.yservice.utilities.Helper;
import com.iyashasgowda.yservice.utilities.MediaType;
import com.iyashasgowda.yservice.utilities.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class MediaService {
    @Autowired
    private Storage storage;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Helper helper;

    public Media saveFile(MultipartFile file, long user_id) {
        Media media = new Media();
        helper.setMediaType(media, file);

        if (media.getType() == MediaType.VIDEO) {
            storage.storeVideo(media, file);
            helper.setVideoMetadata(media, file);
        } else if (media.getType() == MediaType.IMAGE) {
            storage.storeImage(media, file);
            helper.setImageMetadata(media, file);
        } else return null;

        if (media.getFilename() != null) {
            userService.incrementUploads(user_id);
            media.setUser(userService.getUser(user_id));
            return mediaRepository.save(media);
        }
        return null;
    }

    public boolean removeFile(long media_id, long user_id) {
        Media media = mediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            boolean removed = storage.removeMedia(media.getFilename(), media.getType());

            if (removed) {
                mediaRepository.deleteById(media_id);
                userService.decrementUploads(user_id);
                return true;
            }
        }
        return false;
    }

    public void incrementLikes(long user_id) {
        mediaRepository.incrementLikes(user_id);
    }

    public void decrementLikes(long user_id) {
        mediaRepository.decrementLikes(user_id);
    }

    public List<Media> getImages() {
        return mediaRepository.findByType(MediaType.IMAGE);
    }

    public List<Media> getVideos() {
        return mediaRepository.findByType(MediaType.VIDEO);
    }
}

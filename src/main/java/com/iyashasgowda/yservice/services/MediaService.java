package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.entities.User;
import com.iyashasgowda.yservice.repositories.MediaRepository;
import com.iyashasgowda.yservice.utilities.Helper;
import com.iyashasgowda.yservice.utilities.MediaType;
import com.iyashasgowda.yservice.utilities.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.iyashasgowda.yservice.utilities.Constants.MEDIA_PATH;

@Service
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
        String filename = file.getOriginalFilename();
        MediaType type = helper.getMediaType(file);
        long size = helper.getMediaSize(file);
        User user = userService.getUser(user_id);
        String identifier = null;
        String url = null;

        if (type == MediaType.VIDEO) {
            identifier = storage.storeVideo(file);
            url = MEDIA_PATH + "/videos/" + identifier;
        } else if (type == MediaType.IMAGE) {
            identifier = storage.storeImage(file);
            url = MEDIA_PATH + "/images/" + identifier;
        }

        if (identifier != null) {
            userService.incrementUploads(user_id);
            return mediaRepository.save(
                    new Media(
                            identifier,
                            user,
                            filename,
                            size,
                            url,
                            type
                    )
            );
        }
        return null;
    }

    public List<Media> getImages() {
        return mediaRepository.findByType(MediaType.IMAGE);
    }

    public List<Media> getVideos() {
        return mediaRepository.findByType(MediaType.VIDEO);
    }
}

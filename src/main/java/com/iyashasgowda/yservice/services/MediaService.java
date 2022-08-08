package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Media;
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
    private MediaRepository repository;

    @Autowired
    private Helper helper;

    public Media saveFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        MediaType type = helper.getMediaType(file);
        long size = helper.getMediaSize(file);

        if (type == MediaType.VIDEO) {
            String identifier = storage.storeVideo(file);
            String url = MEDIA_PATH + "/videos/" + identifier;

            return repository.save(new Media(0, identifier, filename, size, url, type));
        } else if (type == MediaType.IMAGE) {
            String identifier = storage.storeImage(file);
            String url = MEDIA_PATH + "/images/" + identifier;

            return repository.save(new Media(0, identifier, filename, size, url, type));
        }

        return null;
    }

    public List<Media> getImages() {
        return repository.findByType(MediaType.IMAGE);
    }

    public List<Media> getVideos() {
        return repository.findByType(MediaType.VIDEO);
    }
}

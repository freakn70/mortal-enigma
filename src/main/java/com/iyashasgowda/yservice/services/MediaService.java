package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.repositories.IMediaRepository;
import com.iyashasgowda.yservice.utilities.Helper;
import com.iyashasgowda.yservice.utilities.MediaType;
import com.iyashasgowda.yservice.utilities.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@Transactional
public class MediaService {

    @Autowired
    private Storage storage;

    @Autowired
    private IMediaRepository iMediaRepository;

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
            return iMediaRepository.save(media);
        }
        return null;
    }

    public boolean removeFile(long media_id, long user_id) {
        Media media = iMediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            boolean removed = storage.removeMedia(media.getFilename(), media.getType());

            if (removed) {
                iMediaRepository.deleteById(media_id);
                userService.decrementUploads(user_id);
                return true;
            }
        }
        return false;
    }

    public void incrementViews(long media_id) {
        iMediaRepository.incrementViews(media_id);
    }

    public void incrementLikes(long user_id) {
        iMediaRepository.incrementLikes(user_id);
    }

    public void decrementLikes(long user_id) {
        iMediaRepository.decrementLikes(user_id);
    }

    public void incrementComments(long media_id) {
        iMediaRepository.incrementComments(media_id);
    }

    public void decrementComments(long media_id) {
        iMediaRepository.decrementComments(media_id);
    }

    public void incrementReports(long media_id) {
        iMediaRepository.incrementReports(media_id);
    }

    public List<Media> getImages() {
        return iMediaRepository.findByType(MediaType.IMAGE);
    }

    public List<Media> getVideos() {
        return iMediaRepository.findByType(MediaType.VIDEO);
    }

    public List<Media> getTrendingImages(int limit) {
        return iMediaRepository.findByTypeOrderByLikesDesc(MediaType.IMAGE, PageRequest.of(0, limit));
    }

    public List<Media> getTrendingVideos(int limit) {
        return iMediaRepository.findByTypeOrderByLikesDesc(MediaType.VIDEO, PageRequest.of(0, limit));
    }

    public List<Media> getRelatedVideos(long media_id) {
        Media media = iMediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            Set<String> keywords = new HashSet<>(Arrays.asList(media.getTitle().split(" ")));

            return iMediaRepository.findAll((Specification<Media>) (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("id"), media_id));
                predicates.add(cb.equal(root.get("type"), MediaType.VIDEO));

                /* Conditions for like clause */
                List<Predicate> conditions = new ArrayList<>();
                for (String keyword : keywords)
                    conditions.add(cb.like(root.get("title"), "%" + keyword + "%"));

                predicates.add(cb.or(conditions.toArray(new Predicate[conditions.size()])));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            });
        }
        return getTrendingVideos(10);
    }
}

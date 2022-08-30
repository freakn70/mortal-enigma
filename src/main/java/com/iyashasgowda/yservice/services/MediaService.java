package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.entities.UserMedia;
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
    private LikeService likeService;

    @Autowired
    private Helper helper;

    public List<Media> saveFile(List<MultipartFile> files, long user_id) {
        List<Media> list = new ArrayList<>();

        files.forEach(file -> {
            Media media = new Media();

            helper.setMediaType(media, file);
            if (media.getType() != MediaType.INVALID) {
                if (media.getType() == MediaType.VIDEO) {
                    storage.storeVideo(media, file);
                    helper.setVideoMetadata(media, file);
                } else if (media.getType() == MediaType.IMAGE) {
                    storage.storeImage(media, file);
                    helper.setImageMetadata(media, file);
                }

                if (media.getFilename() != null) {
                    userService.incrementUploads(user_id);
                    media.setUser(userService.getUser(user_id));
                    list.add(iMediaRepository.save(media));
                }
            }
        });

        return list;
    }

    public boolean removeFile(long media_id, long user_id) {
        Media media = iMediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            boolean removed = storage.removeMedia(media.getFilename(), media.getType());

            if (removed) {
                likeService.removeLike(media_id, user_id);
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

    public void incrementReports(long media_id) {
        iMediaRepository.incrementReports(media_id);
    }

    public Media getMediaById(long media_id) {
        return iMediaRepository.findById(media_id).orElse(null);
    }

    public UserMedia getUserMedia(long media_id, long user_id) {
        UserMedia userMedia = null;
        Media media = iMediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            userMedia = new UserMedia();
            userMedia.setId(media_id);
            userMedia.setUser(userService.getUser(user_id));
            userMedia.setFilename(media.getFilename());
            userMedia.setTitle(media.getTitle());
            userMedia.setSize(media.getSize());
            userMedia.setDuration(media.getDuration());
            userMedia.setType(media.getType());
            userMedia.setFormat(media.getFormat());
            userMedia.setWidth(media.getWidth());
            userMedia.setHeight(media.getHeight());
            userMedia.setUrl(media.getUrl());
            userMedia.setThumbnail(media.getThumbnail());
            userMedia.setViews(media.getViews());
            userMedia.setLikes(media.getLikes());
            userMedia.setComments(media.getComments());
            userMedia.setReports(media.getReports());
            userMedia.setCreated_on(media.getCreated_on());
            userMedia.setLiked(likeService.isLikeExist(media_id, user_id));
        }
        return userMedia;
    }

    public List<Media> getImages() {
        return iMediaRepository.findByTypeOrderByIdDesc(MediaType.IMAGE);
    }

    public List<Media> getVideos() {
        return iMediaRepository.findByTypeOrderByIdDesc(MediaType.VIDEO);
    }

    public List<Media> getLikedVideos(long user_id) {
        return likeService.getUserLikedVideos(user_id);
    }

    public List<Media> getLikedImages(long user_id) {
        return likeService.getUserLikedImages(user_id);
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
            Set<String> keywords = new HashSet<>(Arrays.asList(media.getTitle().split("[, ._-]+")));

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
package com.iyashasgowda.mortalenigma.services;

import com.iyashasgowda.mortalenigma.entities.Media;
import com.iyashasgowda.mortalenigma.entities.PageResponse;
import com.iyashasgowda.mortalenigma.entities.UserMedia;
import com.iyashasgowda.mortalenigma.repositories.IMediaRepository;
import com.iyashasgowda.mortalenigma.utilities.Helper;
import com.iyashasgowda.mortalenigma.utilities.MediaType;
import com.iyashasgowda.mortalenigma.utilities.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> saveFile(long user_id, MultipartFile file, String title, String description, String keywords) {
        if (userService.getUser(user_id) == null)
            return new ResponseEntity<>("No owner exist to upload file!", HttpStatus.BAD_REQUEST);

        Media media = new Media();
        media.setTitle(title);
        media.setDescription(description.trim().isEmpty() ? null : description);
        media.setKeywords(keywords.trim().isEmpty() ? null : keywords);

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
                return new ResponseEntity<>(iMediaRepository.save(media), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Error while saving the file!", HttpStatus.INTERNAL_SERVER_ERROR);
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

    public PageResponse getImages(int page, int size) {
        Page<Media> list = iMediaRepository.findByTypeOrderByIdDesc(MediaType.IMAGE, PageRequest.of(page, size));
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getVideos(int page, int size) {
        Page<Media> list = iMediaRepository.findByTypeOrderByIdDesc(MediaType.VIDEO, PageRequest.of(page, size));
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getLikedVideos(long user_id, int page, int size) {
        Page<Media> list = likeService.getUserLikedVideos(user_id, page, size);
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getLikedImages(long user_id, int page, int size) {
        Page<Media> list = likeService.getUserLikedImages(user_id, page, size);
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getTrendingImages(int page, int size) {
        Page<Media> list = iMediaRepository.findByTypeOrderByLikesDesc(MediaType.IMAGE, PageRequest.of(page, size));
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getTrendingVideos(int page, int size) {
        Page<Media> list = iMediaRepository.findByTypeOrderByLikesDesc(MediaType.VIDEO, PageRequest.of(page, size));
        return new PageResponse(
                list.getNumber(),
                list.getNumberOfElements(),
                list.getTotalPages(),
                list.getContent()
        );
    }

    public PageResponse getRelatedVideos(long media_id, int page, int size) {
        Media media = iMediaRepository.findById(media_id).orElse(null);

        if (media != null) {
            Set<String> keywords = new HashSet<>(Arrays.asList(media.getTitle().split("[, ._-]+")));

            Specification<Media> specification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("id"), media_id));
                predicates.add(cb.equal(root.get("type"), MediaType.VIDEO));

                /* Conditions for like clause */
                List<Predicate> conditions = new ArrayList<>();
                for (String keyword : keywords)
                    conditions.add(cb.like(root.get("title"), "%" + keyword + "%"));

                predicates.add(cb.or(conditions.toArray(new Predicate[conditions.size()])));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };

            Page<Media> list = iMediaRepository.findAll(specification, PageRequest.of(page, size));
            return new PageResponse(
                    list.getNumber(),
                    list.getNumberOfElements(),
                    list.getTotalPages(),
                    list.getContent()
            );
        }
        return getTrendingVideos(page, size);
    }
}
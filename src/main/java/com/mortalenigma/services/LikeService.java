package com.mortalenigma.services;

import com.mortalenigma.entities.Like;
import com.mortalenigma.entities.Media;
import com.mortalenigma.repositories.ILikeRepository;
import com.mortalenigma.utilities.Constants.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LikeService {

    @Autowired
    private ILikeRepository iLikeRepository;

    public void addLike(Like like) {
        iLikeRepository.incrementMediaLikes(like.getMedia().getId());
        iLikeRepository.incrementUserLikes(like.getUser().getId());
        iLikeRepository.save(like);
    }

    public void removeLike(long media_id, long user_id) {
        iLikeRepository.decrementMediaLikes(media_id);
        iLikeRepository.decrementUserLikes(user_id);
        iLikeRepository.deleteByMediaIdAndUserId(media_id, user_id);
    }

    public boolean isLikeExist(long media_id, long user_id) {
        return iLikeRepository.isLikeExist(media_id, user_id);
    }

    public Page<Media> getUserLikedVideos(long user_id, int page, int size) {
        Page<Like> likes = iLikeRepository.findByUserIdAndMediaTypeOrderByIdDesc(user_id, MediaType.VIDEO, PageRequest.of(page, size));
        List<Media> media = new ArrayList<>();
        likes.forEach(like -> media.add(like.getMedia()));
        return new PageImpl<>(media);
    }

    public Page<Media> getUserLikedImages(long user_id, int page, int size) {
        Page<Like> likes = iLikeRepository.findByUserIdAndMediaTypeOrderByIdDesc(user_id, MediaType.IMAGE, PageRequest.of(page, size));
        List<Media> media = new ArrayList<>();
        likes.forEach(like -> media.add(like.getMedia()));
        return new PageImpl<>(media);
    }
}

package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Like;
import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.repositories.ILikeRepository;
import com.iyashasgowda.yservice.utilities.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Media> getUserLikedVideos(long user_id) {
        List<Like> likes = iLikeRepository.findByUserIdAndMediaTypeOrderByIdDesc(user_id, MediaType.VIDEO);
        List<Media> media = new ArrayList<>();
        likes.forEach(like -> media.add(like.getMedia()));
        return media;
    }

    public List<Media> getUserLikedImages(long user_id) {
        List<Like> likes = iLikeRepository.findByUserIdAndMediaTypeOrderByIdDesc(user_id, MediaType.IMAGE);
        List<Media> media = new ArrayList<>();
        likes.forEach(like -> media.add(like.getMedia()));
        return media;
    }
}

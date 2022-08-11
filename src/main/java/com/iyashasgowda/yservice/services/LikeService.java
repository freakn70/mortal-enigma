package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.Like;
import com.iyashasgowda.yservice.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    public void addLike(Like like) {
        mediaService.incrementLikes(like.getMedia().getId());
        userService.incrementLikes(like.getUser().getId());

        likeRepository.save(like);
    }

    public void removeLike(long media_id, long user_id) {
        mediaService.decrementLikes(media_id);
        userService.decrementLikes(user_id);

        likeRepository.deleteByMediaIdAndUserId(media_id, user_id);
    }
}

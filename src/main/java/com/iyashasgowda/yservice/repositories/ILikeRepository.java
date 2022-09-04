package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.Like;
import com.iyashasgowda.yservice.utilities.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends PagingAndSortingRepository<Like, Long> {
    void deleteByMediaIdAndUserId(long media_id, long user_id);

    Page<Like> findByUserIdAndMediaTypeOrderByIdDesc(long user_id, MediaType type, Pageable page);

    @Modifying
    @Query("UPDATE Media m SET m.likes = m.likes + 1 WHERE m.id = ?1")
    void incrementMediaLikes(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.likes = m.likes - 1 WHERE m.id = ?1 AND m.likes > 0")
    void decrementMediaLikes(long media_id);

    @Modifying
    @Query("UPDATE User u SET u.likes = u.likes + 1 WHERE u.id = ?1")
    void incrementUserLikes(long user_id);

    @Modifying
    @Query("UPDATE User u SET u.likes = u.likes - 1 WHERE u.id = ?1 AND u.likes > 0")
    void decrementUserLikes(long user_id);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.media.id = ?1 AND l.user.id = ?2")
    boolean isLikeExist(long media_id, long user_id);
}

package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByMediaIdAndUserId(long media_id, long user_id);
}

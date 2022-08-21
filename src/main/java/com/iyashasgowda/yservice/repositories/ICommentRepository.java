package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMedia_Id(long media_id);

    List<Comment> findByMedia_IdAndUser_Id(long media_id, long user_id);
}

package com.iyashasgowda.mortalenigma.repositories;

import com.iyashasgowda.mortalenigma.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMedia_Id(long media_id);

    List<Comment> findByMedia_IdAndUser_Id(long media_id, long user_id);

    @Modifying
    @Query("UPDATE Media m SET m.comments = m.comments + 1 WHERE m.id = ?1")
    void incrementComments(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.comments = m.comments - 1 WHERE m.id = ?1 AND m.comments > 0")
    void decrementComments(long media_id);
}

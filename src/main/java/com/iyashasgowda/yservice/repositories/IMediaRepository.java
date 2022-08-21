package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.utilities.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMediaRepository extends JpaRepository<Media, Long>, JpaSpecificationExecutor<Media> {

    List<Media> findByType(MediaType type);

    List<Media> findByTypeOrderByLikesDesc(MediaType type, Pageable pageable);

    @Modifying
    @Query("UPDATE Media m SET m.views = m.views + 1 WHERE m.id = ?1")
    void incrementViews(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.likes = m.likes + 1 WHERE m.id = ?1")
    void incrementLikes(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.likes = m.likes - 1 WHERE m.id = ?1 AND m.likes > 0")
    void decrementLikes(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.comments = m.comments + 1 WHERE m.id = ?1")
    void incrementComments(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.comments = m.comments - 1 WHERE m.id = ?1 AND m.comments > 0")
    void decrementComments(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.reports = m.reports + 1 WHERE m.id = ?1")
    void incrementReports(long media_id);
}

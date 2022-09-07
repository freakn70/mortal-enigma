package com.iyashasgowda.mortalenigma.repositories;

import com.iyashasgowda.mortalenigma.entities.Media;
import com.iyashasgowda.mortalenigma.utilities.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaRepository extends PagingAndSortingRepository<Media, Long>, JpaSpecificationExecutor<Media> {

    Page<Media> findAll(Specification<Media> spec, Pageable page);

    Page<Media> findByTypeOrderByIdDesc(MediaType type, Pageable page);

    Page<Media> findByTypeOrderByLikesDesc(MediaType type, Pageable page);

    @Modifying
    @Query("UPDATE Media m SET m.views = m.views + 1 WHERE m.id = ?1")
    void incrementViews(long media_id);

    @Modifying
    @Query("UPDATE Media m SET m.reports = m.reports + 1 WHERE m.id = ?1")
    void incrementReports(long media_id);
}

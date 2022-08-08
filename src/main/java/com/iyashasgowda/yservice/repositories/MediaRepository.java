package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.utilities.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByType(MediaType type);
}

package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByUsername(String username);

    @Modifying
    @Query("update User u SET u.uploads = u.uploads + 1 WHERE u.id = ?1")
    void incrementUploads(long user_id);
}

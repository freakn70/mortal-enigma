package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Query("update User u SET u.uploads = u.uploads + 1 WHERE u.id = ?1")
    void incrementUploads(long user_id);

    @Modifying
    @Query("update User u SET u.uploads = u.uploads - 1 WHERE u.id = ?1 AND u.uploads > 0")
    void decrementUploads(long user_id);

    @Modifying
    @Query("update User u SET u.likes = u.likes + 1 WHERE u.id = ?1")
    void incrementLikes(long user_id);

    @Modifying
    @Query("update User u SET u.likes = u.likes - 1 WHERE u.id = ?1 AND u.likes > 0")
    void decrementLikes(long user_id);
}

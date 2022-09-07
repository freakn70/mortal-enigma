package com.iyashasgowda.mortalenigma.repositories;

import com.iyashasgowda.mortalenigma.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.uploads = u.uploads + 1 WHERE u.id = ?1")
    void incrementUploads(long user_id);

    @Modifying
    @Query("UPDATE User u SET u.uploads = u.uploads - 1 WHERE u.id = ?1 AND u.uploads > 0")
    void decrementUploads(long user_id);
}

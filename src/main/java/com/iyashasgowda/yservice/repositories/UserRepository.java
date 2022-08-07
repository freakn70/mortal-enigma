package com.iyashasgowda.yservice.repositories;

import com.iyashasgowda.yservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByUsername(String username);
}

package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.User;
import com.iyashasgowda.yservice.repositories.UserRepository;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private Helper helper;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(long id) {
        return repository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user.setPassword(helper.encrypt(user.getPassword()));
        return repository.save(user);
    }

    public User updateUser(User user) {
        user.setPassword(helper.encrypt(user.getPassword()));
        return repository.save(user);
    }

    public void incrementUploads(long user_id) {
        repository.incrementUploads(user_id);
    }

    public void decrementUploads(long user_id) {
        repository.decrementUploads(user_id);
    }

    public void incrementLikes(long user_id) {
        repository.incrementLikes(user_id);
    }

    public void decrementLikes(long user_id) {
        repository.decrementLikes(user_id);
    }

    public boolean validateUsername(String username) {
        return repository.findUsersByUsername(username).size() == 0;
    }

    public boolean deleteUSer(long id) {
        repository.deleteById(id);
        return true;
    }
}

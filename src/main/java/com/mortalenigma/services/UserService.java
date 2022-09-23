package com.mortalenigma.services;

import com.mortalenigma.entities.User;
import com.mortalenigma.repositories.IUserRepository;
import com.mortalenigma.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private Helper helper;

    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    public User getUser(long id) {
        return iUserRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }

    public User getUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    public User createUser(User user) {
        User existingUser = iUserRepository.findByUsername(user.getUsername());
        if (existingUser != null) user.setUsername(helper.generateUsername(user.getUsername()));
        return iUserRepository.save(user);
    }

    public User updateUser(User user) {
        return iUserRepository.save(user);
    }

    public void incrementUploads(long user_id) {
        iUserRepository.incrementUploads(user_id);
    }

    public void decrementUploads(long user_id) {
        iUserRepository.decrementUploads(user_id);
    }

    public boolean validateUsername(String username) {
        return iUserRepository.findByUsername(username) == null;
    }

    public boolean deleteUser(long id) {
        iUserRepository.deleteById(id);
        return true;
    }
}

package com.iyashasgowda.yservice.services;

import com.iyashasgowda.yservice.entities.User;
import com.iyashasgowda.yservice.repositories.UserRepository;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private Helper helper;

    public List<User> getAllUsers() {
        return repository
                .findAll()
                .stream()
                .peek(u -> u.setPassword(helper.decrypt(u.getPassword())))
                .collect(Collectors.toList());
    }

    public User getUser(long id) {
        User user = repository.findById(id).orElse(null);

        if (user != null) {
            user.setPassword(helper.decrypt(user.getPassword()));
            return user;
        }
        return null;
    }

    public User createUser(User user) {
        user.setActive(true);
        user.setPassword(helper.encrypt(user.getPassword()));
        return repository.save(user);
    }

    public User updateUser(User user) {
        user.setPassword(helper.encrypt(user.getPassword()));
        return repository.save(user);
    }

    public boolean validateUsername(String username) {
        return repository.findUsersByUsername(username).size() == 0;
    }

    public boolean deleteUSer(long id) {
        repository.deleteById(id);
        return true;
    }
}

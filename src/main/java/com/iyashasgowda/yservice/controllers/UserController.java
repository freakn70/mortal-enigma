package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.User;
import com.iyashasgowda.yservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            User user = service.getUser(id);

            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
        try {
            User user = service.getUserByEmail(email);

            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>("No user found with email " + email, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (user != null)
                return new ResponseEntity<>(service.createUser(user), HttpStatus.OK);
            else
                return new ResponseEntity<>("Invalid request body!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/validate/{username}")
    public ResponseEntity<?> validateUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(service.validateUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (user != null) {
                return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);
            } else
                return new ResponseEntity<>("Invalid request body!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            if (service.deleteUser(id))
                return new ResponseEntity<>("success", HttpStatus.OK);
            else
                return new ResponseEntity<>("Error while deleting the user!", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

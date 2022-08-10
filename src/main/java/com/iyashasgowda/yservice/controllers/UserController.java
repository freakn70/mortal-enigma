package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.User;
import com.iyashasgowda.yservice.services.UserService;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private Helper helper;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return helper.successResponse(service.getAllUsers());
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            User user = service.getUser(id);

            if (user != null)
                return helper.successResponse(user);
            else
                return helper.customResponse(HttpStatus.NOT_FOUND, "No user found!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (user != null)
                return helper.successResponse(service.createUser(user));
            else
                return helper.customResponse(HttpStatus.BAD_REQUEST, "Invalid request body!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @GetMapping("/validate/{username}")
    public ResponseEntity<?> getUsernames(@PathVariable("username") String username) {
        try {
            return helper.successResponse(service.validateUsername(username));
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (user != null) {
                return helper.successResponse(service.updateUser(user));
            } else
                return helper.customResponse(HttpStatus.BAD_REQUEST, "Invalid request body!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            if (service.deleteUSer(id))
                return helper.successResponse(null);
            else
                return helper.customResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting the user!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }
}

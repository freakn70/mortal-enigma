package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.Utils.DataResponse;
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

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity
                    .ok()
                    .body(new DataResponse(HttpStatus.OK, "Success", service.getAllUsers()));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            User user = service.getUser(id);

            if (user != null)
                return ResponseEntity
                        .ok()
                        .body(new DataResponse(HttpStatus.OK, "Success", user));
            else
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new DataResponse(HttpStatus.NOT_FOUND, "No user found!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (user != null) {
                return ResponseEntity
                        .ok()
                        .body(new DataResponse(HttpStatus.OK, "Success", service.createUser(user)));
            } else
                return ResponseEntity
                        .badRequest()
                        .body(new DataResponse(HttpStatus.BAD_REQUEST, "Invalid request body!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    @GetMapping("/validate/{username}")
    public ResponseEntity<?> getUsernames(@PathVariable("username") String username) {
        try {
            return ResponseEntity
                    .ok()
                    .body(new DataResponse(HttpStatus.OK, "Success", service.validateUsername(username)));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (user != null) {
                return ResponseEntity
                        .ok()
                        .body(new DataResponse(HttpStatus.OK, "Success", service.updateUser(user)));
            } else
                return ResponseEntity
                        .badRequest()
                        .body(new DataResponse(HttpStatus.BAD_REQUEST, "Invalid request body!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            if (service.deleteUSer(id))
                return ResponseEntity
                        .ok()
                        .body(new DataResponse(HttpStatus.OK, "Success", null));
            else
                return ResponseEntity
                        .internalServerError()
                        .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting the user!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }
}

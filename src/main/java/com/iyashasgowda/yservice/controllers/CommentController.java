package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Comment;
import com.iyashasgowda.yservice.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        try {
            return new ResponseEntity<>(service.saveComment(comment), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{media_id}")
    public ResponseEntity<?> getComments(@PathVariable("media_id") long media_id) {
        try {
            return new ResponseEntity<>(service.getComments(media_id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{media_id}/{user_id}")
    public ResponseEntity<?> getComments(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            return new ResponseEntity<>(service.getComments(media_id, user_id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

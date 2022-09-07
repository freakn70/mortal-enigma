package com.iyashasgowda.mortalenigma.controllers;

import com.iyashasgowda.mortalenigma.entities.Comment;
import com.iyashasgowda.mortalenigma.services.CommentService;
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

    @DeleteMapping("/remove/{media_id}/{comment_id}")
    public ResponseEntity<?> removeComment(@PathVariable("media_id") long media_id, @PathVariable("comment_id") long comment_id) {
        try {
            if (service.removeComment(media_id, comment_id))
                return new ResponseEntity<>("success", HttpStatus.OK);
            else
                return new ResponseEntity<>("Failed to delete comment!", HttpStatus.INTERNAL_SERVER_ERROR);
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

package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Comment;
import com.iyashasgowda.yservice.services.CommentService;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @Autowired
    private Helper helper;

    @PostMapping("/")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        try {
            return helper.successResponse(service.saveComment(comment));
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @GetMapping("/{media_id}")
    public ResponseEntity<?> getComments(@PathVariable("media_id") long media_id) {
        try {
            return helper.successResponse(service.getComments(media_id));
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @GetMapping("/{media_id}/{user_id}")
    public ResponseEntity<?> getComments(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            return helper.successResponse(service.getComments(media_id, user_id));
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }
}

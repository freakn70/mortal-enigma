package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Like;
import com.iyashasgowda.yservice.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService service;

    @PostMapping
    public ResponseEntity<?> addLike(@RequestBody Like like) {
        try {
            service.addLike(like);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{media_id}/{user_id}")
    public ResponseEntity<?> removeLike(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            service.removeLike(media_id, user_id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

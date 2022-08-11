package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Like;
import com.iyashasgowda.yservice.services.LikeService;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService service;

    @Autowired
    private Helper helper;

    @PostMapping
    public ResponseEntity<?> addLike(@RequestBody Like like) {
        try {
            service.addLike(like);
            return helper.successResponse(null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @DeleteMapping("/{media_id}/{user_id}")
    public ResponseEntity<?> removeLike(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            service.removeLike(media_id, user_id);
            return helper.successResponse(null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }
}

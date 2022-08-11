package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.services.MediaService;
import com.iyashasgowda.yservice.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService service;

    @Autowired
    private Helper helper;

    @GetMapping("/videos")
    public ResponseEntity<?> getVideos() {
        try {
            return helper.successResponse(service.getVideos());
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @GetMapping("/images")
    public ResponseEntity<?> getImages() {
        try {
            return helper.successResponse(service.getImages());
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam MultipartFile file, @RequestParam long user_id) {
        try {
            Media media = service.saveFile(file, user_id);

            if (media != null)
                return helper.successResponse(media);
            else
                return helper.customResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }

    @DeleteMapping("/remove/{media_id}/{user_id}")
    public ResponseEntity<?> removeMedia(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            if (service.removeFile(media_id, user_id))
                return helper.successResponse(null);
            else
                return helper.customResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete media!", null);
        } catch (Exception e) {
            return helper.errorResponse(e);
        }
    }
}

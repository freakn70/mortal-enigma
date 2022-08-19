package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.services.MediaService;
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

    @GetMapping("/videos")
    public ResponseEntity<?> getVideos() {
        try {
            return new ResponseEntity<>(service.getVideos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images")
    public ResponseEntity<?> getImages() {
        try {
            return new ResponseEntity<>(service.getImages(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/trending/{limit}")
    public ResponseEntity<?> getTrendingImages(@PathVariable("limit") int limit) {
        try {
            return new ResponseEntity<>(service.getTrendingImages(limit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam MultipartFile file, @RequestParam long user_id) {
        try {
            if (file.isEmpty())
                return new ResponseEntity<>("No file found to upload!", HttpStatus.BAD_REQUEST);

            Media media = service.saveFile(file, user_id);

            if (media != null)
                return new ResponseEntity<>(media, HttpStatus.OK);
            else
                return new ResponseEntity<>("Error while saving the file!", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{media_id}/{user_id}")
    public ResponseEntity<?> removeMedia(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            if (service.removeFile(media_id, user_id))
                return new ResponseEntity<>("success", HttpStatus.OK);
            else
                return new ResponseEntity<>("Failed to delete media!", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.mortalenigma.controllers;

import com.mortalenigma.entities.Media;
import com.mortalenigma.entities.UserMedia;
import com.mortalenigma.services.MediaService;
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

    @PutMapping("/views/{media_id}")
    public ResponseEntity<?> incrementViews(@PathVariable("media_id") long media_id) {
        try {
            service.incrementViews(media_id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reports/{media_id}")
    public ResponseEntity<?> incrementReports(@PathVariable("media_id") long media_id) {
        try {
            service.incrementReports(media_id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{media_id}")
    public ResponseEntity<?> getMedia(@PathVariable("media_id") long media_id) {
        try {
            Media media = service.getMediaById(media_id);

            if (media != null)
                return new ResponseEntity<>(media, HttpStatus.OK);
            else
                return new ResponseEntity<>("Media not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{media_id}/{user_id}")
    public ResponseEntity<?> getMediaWithUser(@PathVariable("media_id") long media_id, @PathVariable("user_id") long user_id) {
        try {
            UserMedia media = service.getUserMedia(media_id, user_id);

            if (media != null)
                return new ResponseEntity<>(media, HttpStatus.OK);
            else
                return new ResponseEntity<>("Media not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/videos")
    public ResponseEntity<?> getVideos(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return new ResponseEntity<>(service.getVideos(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images")
    public ResponseEntity<?> getImages(@RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getImages(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/videos/{user_id}")
    public ResponseEntity<?> getLikedVideos(@PathVariable("user_id") long user_id, @RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getLikedVideos(user_id, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{user_id}")
    public ResponseEntity<?> getLikedImages(@PathVariable("user_id") long user_id, @RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getLikedImages(user_id, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/trending")
    public ResponseEntity<?> getTrendingImages(@RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getTrendingImages(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/videos/trending")
    public ResponseEntity<?> getTrendingVideos(@RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getTrendingVideos(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/videos/related/{media_id}")
    public ResponseEntity<?> getRelatedVideos(@PathVariable("media_id") long media_id, @RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(service.getRelatedVideos(media_id, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload/{user_id}")
    public ResponseEntity<?> uploadMedia(@PathVariable("user_id") long user_id, @RequestParam MultipartFile file,
                                         @RequestParam String title, @RequestParam String description, @RequestParam String keywords) {
        try {
            if (file == null)
                return new ResponseEntity<>("No file found to upload!", HttpStatus.BAD_REQUEST);

            return service.saveFile(user_id, file, title, description, keywords);
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
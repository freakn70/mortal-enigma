package com.iyashasgowda.yservice.controllers;

import com.iyashasgowda.yservice.entities.Media;
import com.iyashasgowda.yservice.services.MediaService;
import com.iyashasgowda.yservice.utilities.DataResponse;
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
            return new ResponseEntity<>(
                    new DataResponse(HttpStatus.OK, "success", service.getVideos()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/images")
    public ResponseEntity<?> getImages() {
        try {
            return new ResponseEntity<>(
                    new DataResponse(HttpStatus.OK, "success", service.getImages()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedia(@RequestParam MultipartFile file) {
        try {
            Media media = service.saveFile(file);

            if (media != null)
                return new ResponseEntity<>(
                        new DataResponse(HttpStatus.OK, "success", media),
                        HttpStatus.OK
                );
            else
                return new ResponseEntity<>(
                        new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file!", null),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new DataResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

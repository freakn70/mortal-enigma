package com.mortalenigma.controllers;

import com.mortalenigma.entities.Email;
import com.mortalenigma.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody Email email) {
        if (email != null) {
            return new ResponseEntity<>(service.sendEmail(email), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid request body!", HttpStatus.BAD_REQUEST);
        }
    }
}

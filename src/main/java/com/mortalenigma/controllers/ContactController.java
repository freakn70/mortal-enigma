package com.mortalenigma.controllers;

import com.mortalenigma.entities.Email;
import com.mortalenigma.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService service;

    @PostMapping("/send-email-verification-otp")
    public ResponseEntity<?> sendEmailVerificationOtp(@RequestParam String email) {
        if (email != null) {
            return new ResponseEntity<>(service.sendEmailVerificationOtp(email), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid request body!", HttpStatus.BAD_REQUEST);
        }
    }
}

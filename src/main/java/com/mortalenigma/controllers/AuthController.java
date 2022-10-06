package com.mortalenigma.controllers;

import com.mortalenigma.entities.Auth;
import com.mortalenigma.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Auth auth) {
        return authService.authenticate(auth);
    }
}

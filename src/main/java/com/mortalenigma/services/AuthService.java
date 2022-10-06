package com.mortalenigma.services;

import com.mortalenigma.entities.Auth;
import com.mortalenigma.entities.Token;
import com.mortalenigma.utilities.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthUserService authUserService;

    public ResponseEntity<?> authenticate(Auth auth) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }

        final String token = jwtTokenUtil.generateToken(authUserService.loadUserByUsername(auth.getUsername()));
        return ResponseEntity.ok(new Token(token));
    }
}

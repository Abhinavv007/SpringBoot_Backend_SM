package com.abhinav.social.media.app.controller;

import com.abhinav.social.media.app.response.AuthResponse;
import com.abhinav.social.media.app.models.User;
import com.abhinav.social.media.app.service.UserService;
import com.abhinav.social.media.app.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User newUser) {
        try {
            User isExist = userService.getUserByEmail(newUser.getEmail());

            if (isExist == null) {
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
                User savedUser = userService.createUser(newUser);
                Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
                String token = JwtProvider.generateToken(authentication);
                AuthResponse resp = new AuthResponse(token, "Register Success");
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new AuthResponse(null, "Email Already Exists"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse(null, "Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        try {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(existingUser.getEmail(), existingUser.getPassword());
                String token = JwtProvider.generateToken(authentication);
                AuthResponse resp = new AuthResponse(token, "Login Success");
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new AuthResponse(null, "Invalid Email or Password"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse(null, "Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
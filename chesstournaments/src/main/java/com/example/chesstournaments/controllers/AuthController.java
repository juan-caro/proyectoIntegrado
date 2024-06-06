package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.User;
import com.example.chesstournaments.requests.UserLoginRequest;
import com.example.chesstournaments.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            System.out.println("no nulo");
            return ResponseEntity.ok(user);
        } else {
            System.out.println("nulo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}


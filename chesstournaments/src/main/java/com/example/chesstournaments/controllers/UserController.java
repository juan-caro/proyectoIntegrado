package com.example.chesstournaments.controllers;

import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.UserService;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/users/userID")).body(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.getAllUsers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }
}

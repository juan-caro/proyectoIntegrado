package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Platform;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {
    private final PlatformService platformService;

    @PostMapping
    public ResponseEntity<Platform> createPlatform(@RequestBody Platform platform) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/platforms/platformID")).body(platformService.createPlatform(platform));
    }

    @GetMapping
    public ResponseEntity<Page<Platform>> getPlatforms(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("platform " + platformService.getAllPlatforms(page, size) );
        return ResponseEntity.ok().body(platformService.getAllPlatforms(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatform(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(platformService.getPlatform(id));
    }
}

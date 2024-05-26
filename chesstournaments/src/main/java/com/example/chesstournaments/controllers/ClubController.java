package com.example.chesstournaments.controllers;

import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.Club;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.ClubService;

import java.net.URI;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/clubs/clubID")).body(clubService.createClub(club));
    }

    @GetMapping
    public ResponseEntity<Page<Club>> getClubs(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(clubService.getAllClubs(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(clubService.getClub(id));
    }

}

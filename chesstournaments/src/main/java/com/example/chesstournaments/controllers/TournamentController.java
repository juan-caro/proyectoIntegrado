package com.example.chesstournaments.controllers;

import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.TournamentService;

import java.net.URI;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/tournaments/tournamentID")).body(tournamentService.createTournament(tournament));
    }

    @GetMapping
    public ResponseEntity<Page<Tournament>> getTournaments(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(tournamentService.getAllTournaments(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(tournamentService.getTournament(id));
    }
}

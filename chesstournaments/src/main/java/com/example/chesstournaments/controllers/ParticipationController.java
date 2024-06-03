package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Participation;
import com.example.chesstournaments.models.Tournament;
import com.example.chesstournaments.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping("/register")
    public ResponseEntity<Participation> register(@RequestParam String tournamentId, @RequestParam String userId) {
        Participation participation = participationService.registerParticipation(tournamentId, userId);
        return new ResponseEntity<>(participation, HttpStatus.CREATED);
    }

    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregister(@RequestParam String tournamentId, @RequestParam String userId) {
        participationService.unregisterParticipation(tournamentId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/isRegistered")
    public ResponseEntity<Boolean> isRegistered(@RequestParam String tournamentId, @RequestParam String userId) {
        System.out.println("entro");
        boolean isRegistered = participationService.isRegistered(tournamentId, userId);
        System.out.println("eeeeeeee" + isRegistered);
        return new ResponseEntity<>(isRegistered, HttpStatus.OK);
    }

    @GetMapping("/userTournaments")
    public ResponseEntity<List<Tournament>> getUserTournaments(@RequestParam String userId) {
        List<Tournament> tournaments = participationService.getUserTournaments(userId);
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }
}
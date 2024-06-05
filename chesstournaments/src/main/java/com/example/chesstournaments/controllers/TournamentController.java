package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.TournamentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    public static final String PHOTO_DIRECTORY =  "C:\\Users\\Morius\\OneDrive\\Documentos\\Springboot\\chesstournaments\\tournaments\\image\\";
    private final TournamentService tournamentService;
    private final UserRepo userRepository;

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Map<String, Object> payload) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        String name = (String) payload.get("name");
        LocalDateTime dateTime = LocalDateTime.parse((String) payload.get("dateTime"));
        String format = (String) payload.get("format");
        String state = (String) payload.get("state");
        Long rounds = Long.parseLong((String) payload.get("rounds"));
        String creatorId = (String) payload.get("creator_id");


        User creator = userRepository.findById(creatorId).orElse(null);

        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setDateTime(dateTime);
        tournament.setFormat(format);
        tournament.setState(state);
        tournament.setRounds(rounds);
        tournament.setCreator(creator);

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

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(tournamentService.uploadPhoto(id, file));
    }


    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Tournament>> getRecentTournaments() {
        List<Tournament> tournaments = tournamentService.getRecentTournaments();
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }
}

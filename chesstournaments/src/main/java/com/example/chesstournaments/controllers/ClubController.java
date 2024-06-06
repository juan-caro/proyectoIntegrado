package com.example.chesstournaments.controllers;

import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.Club;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.ClubService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {
    public static final String PHOTO_DIRECTORY =  "C:\\Users\\Morius\\OneDrive\\Documentos\\Springboot\\chesstournaments\\clubs\\image\\";
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

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(clubService.uploadPhoto(id, file));
    }


    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

}

package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.ClubRepo;
import com.example.chesstournaments.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.Club;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.ClubService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {
    public static final String PHOTO_DIRECTORY =  "C:\\Users\\johnm\\OneDrive\\Documentos\\Springboot\\chesstournaments\\clubs\\image\\";
    private final ClubService clubService;
    private final UserRepo userRepository;
    private final ClubRepo clubRepo;

    @PostMapping
    public ResponseEntity<Club> createClub(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String description = (String) payload.get("description");
        String iconUrl = (String) payload.get("iconUrl");
        String creatorId = (String) payload.get("creator_id");
        System.out.println("creator id" + creatorId);

        // Obtener el usuario creador del club

        User creator = userRepository.findById(creatorId).orElse(null);

        System.out.println("creator only " +creator);
        // Crear una nueva instancia de Club
        Club club = new Club();
        club.setName(name);
        club.setDescription(description);
        club.setIconUrl(iconUrl);
        club.setCreator(creator);

        // Guardar el club en la base de datos
        Club newClub = clubService.createClub(club);

        // Devolver una respuesta con el club creado y el código de estado 201 (Created)
        return ResponseEntity.created(URI.create("/clubs/clubID")).body(clubService.createClub(club));
    }


    @GetMapping
    public ResponseEntity<Page<Club>> getClubs(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("ALL CLUBS: " + clubService.getAllClubs(page, size).getContent());
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

    @PostMapping("/{id}/join")
    public ResponseEntity<String> joinClub(@PathVariable String id, @RequestParam String userId) {
        clubService.joinClub(id, userId);
        Club club =clubService.getClub(id);
        for(User member: club.getMembers()){
            System.out.println("aaaaaa " + member.getId() + " eeee " + userId);
        }
        return ResponseEntity.ok("Joined the club successfully");
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<String> leaveClub(@PathVariable String id, @RequestParam String userId) {
        clubService.leaveClub(id, userId);
        return ResponseEntity.ok("Left the club successfully");
    }

    @GetMapping("/isMember")
    public ResponseEntity<Boolean> isUserMemberOfClub(@RequestParam String clubId, @RequestParam String userId) {
        boolean isMember = clubService.isUserMemberOfClub(clubId, userId);
        System.out.println("isMember: " + isMember);
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

    @GetMapping("/creator")
    public ResponseEntity<User> getCreatorOfClub(@RequestParam String clubId){
        Club club = clubService.getClub(clubId);

        return new ResponseEntity<>(club.getCreator(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable String id) {
        try {
            Club club = clubService.getClub(id);
            User user = userRepository.findUserById(club.getCreator().getId());
            System.out.println("CLUB: " + club);
            for (User u : club.getMembers()){
                u.setClub(null);
            }
            for (User u: club.getVoters()){
                u.getVotedClubs().remove(club);
            }
            club.getMembers().clear();
            club.getVoters().clear();
            club.setCreator(null);

            clubRepo.save(club);
            user.setCreatedClub(null);
            userRepository.save(user);
            clubService.deleteClub(club);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<String> voteForClub(@PathVariable String id, @RequestParam String userId) {
        System.out.println("entro");
        try {
            clubService.voteForClub(id, userId);
            return ResponseEntity.ok("Voted for the club successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error voting for the club: " + e.getMessage());
        }
    }


}

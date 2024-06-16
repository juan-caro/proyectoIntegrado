package com.example.chesstournaments.controllers;

import com.example.chesstournaments.dto.TournamentDTO;
import com.example.chesstournaments.exceptions.CustomNotFoundException;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

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

/**
 * @file TournamentController.java
 * @brief Controlador REST para manejar torneos de ajedrez.
 * Este controlador ofrece endpoints para crear, obtener, actualizar, eliminar y manejar fotos de torneos.
 * Utiliza el servicio TournamentService para realizar operaciones con los objetos Tournament.
 *
 * @autor Juan Cabello Rodríguez
 */
@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    public static final String PHOTO_DIRECTORY =  "C:\\Users\\Morius\\OneDrive\\Documentos\\Springboot\\chesstournaments\\tournaments\\image\\";
    private final TournamentService tournamentService;
    private final UserRepo userRepository;

    /**
     * Endpoint para crear un nuevo torneo.
     *
     * @param payload Un mapa con los detalles del torneo.
     * @return Una ResponseEntity con el torneo creado y la URI de su ubicación.
     */
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

    /**
     * Endpoint para obtener una lista paginada de torneos.
     *
     * @param page Número de la página a obtener (por defecto es 0).
     * @param size Tamaño de la página (por defecto es 10).
     * @return Una ResponseEntity con una página de torneos.
     */
    @GetMapping
    public ResponseEntity<Page<Tournament>> getTournaments(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(tournamentService.getAllTournaments(page, size));
    }

    /**
     * Endpoint para obtener los detalles de un torneo específico.
     *
     * @param id El ID del torneo a obtener.
     * @return Una ResponseEntity con los detalles del torneo solicitado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(tournamentService.getTournament(id));
    }

    /**
     * Endpoint para subir una foto de un torneo.
     *
     * @param id   El ID del torneo.
     * @param file El archivo de la foto.
     * @return Una ResponseEntity con el mensaje de éxito.
     */
    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(tournamentService.uploadPhoto(id, file));
    }

    /**
     * Endpoint para obtener la foto de un torneo.
     *
     * @param filename El nombre del archivo de la foto.
     * @return Un array de bytes que representa la foto.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    /**
     * Endpoint para obtener una lista de torneos recientes.
     *
     * @return Una ResponseEntity con una lista de torneos recientes.
     */
    @GetMapping("/recent")
    public ResponseEntity<List<Tournament>> getRecentTournaments() {
        List<Tournament> tournaments = tournamentService.getRecentTournaments();
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }

    /**
     * Maneja la excepción CustomNotFoundException.
     *
     * @param e La excepción lanzada.
     * @return Una ResponseEntity con el mensaje de error y el estado HTTP NOT_FOUND.
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> handleCustomNotFoundException(CustomNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint para obtener los torneos creados por un usuario específico.
     *
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con una lista de torneos creados por el usuario.
     */
    @GetMapping("/creatorTournaments")
    public ResponseEntity<List<Tournament>> getCreatorTournaments(@RequestParam String userId) {
        List<Tournament> tournaments = tournamentService.getCreatorTournaments(userId);

        for (Tournament t : tournaments){
            System.out.println("Tournament: " + t.getCreator().getUsername());
        }

        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar un torneo.
     *
     * @param tournamentId El ID del torneo a eliminar.
     * @return Una ResponseEntity con el estado HTTP NO_CONTENT.
     */
    @DeleteMapping("/eliminate")
    public ResponseEntity<Void> deleteTournament(@RequestParam String tournamentId) {
        tournamentService.deleteTournament(tournamentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para actualizar un torneo.
     *
     * @param id    El ID del torneo a actualizar.
     * @param torneo Un DTO con los nuevos datos del torneo.
     * @return Una ResponseEntity con el torneo actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable String id, @RequestBody TournamentDTO torneo) {
        System.out.println("entro");

        System.out.println("datos recibidos, updateo");
        Tournament updatedTournament = tournamentService.updateTournament(id, torneo.getName(), torneo.getDateTime(), torneo.getFormat(), torneo.getState(), torneo.getRounds(), torneo.getPlataformId());
        System.out.println("updateado, devuelvo respuesta");
        return ResponseEntity.ok().body(updatedTournament);
    }
}

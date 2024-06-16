package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Participation;
import com.example.chesstournaments.models.Tournament;
import com.example.chesstournaments.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @file ParticipationController.java
 * @brief Controlador REST para manejar la relación entre participantes(usuarios) y torneos.
 * Este controlador ofrece endpoints para registrar y eliminar participantes en torneos,
 * verificar registros y obtener torneos de un usuario específico.
 * Utiliza el servicio ParticipationService para realizar operaciones con los objetos Participation y Tournament.
 *
 * @autor Juan Cabello Rodríguez
 */

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    /**
     * Endpoint para registrar a un usuario en un torneo.
     *
     * @param tournamentId El ID del torneo.
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con la participación registrada y el código de estado HTTP 201 (CREATED).
     */
    @PostMapping("/register")
    public ResponseEntity<Participation> register(@RequestParam String tournamentId, @RequestParam String userId) {
        Participation participation = participationService.registerParticipation(tournamentId, userId);
        return new ResponseEntity<>(participation, HttpStatus.CREATED);
    }

    /**
     * Endpoint para eliminar el registro de un usuario en un torneo.
     *
     * @param tournamentId El ID del torneo.
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con el código de estado HTTP 204 (NO_CONTENT).
     */
    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregister(@RequestParam String tournamentId, @RequestParam String userId) {
        participationService.unregisterParticipation(tournamentId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para verificar si un usuario está registrado en un torneo.
     *
     * @param tournamentId El ID del torneo.
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con un booleano indicando si el usuario está registrado y el código de estado HTTP 200 (OK).
     */
    @GetMapping("/isRegistered")
    public ResponseEntity<Boolean> isRegistered(@RequestParam String tournamentId, @RequestParam String userId) {
        System.out.println("entro");
        boolean isRegistered = participationService.isRegistered(tournamentId, userId);
        System.out.println("eeeeeeee" + isRegistered);
        return new ResponseEntity<>(isRegistered, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener los torneos en los que un usuario está registrado.
     *
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con la lista de torneos y el código de estado HTTP 200 (OK).
     */
    @GetMapping("/userTournaments")
    public ResponseEntity<List<Tournament>> getUserTournaments(@RequestParam String userId) {
        List<Tournament> tournaments = participationService.getUserTournaments(userId);
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }
}
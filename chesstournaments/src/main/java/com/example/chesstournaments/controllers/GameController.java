package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Game;
import com.example.chesstournaments.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @file GameController.java
 * @brief Controlador REST para manejar la relación entre plataforma y torneo.
 * Este controlador ofrece endpoints para la creación, recuperación y eliminación de juegos.
 * Utiliza el servicio GameService para realizar operaciones con los objetos Game.
 *
 * @autor Juan Cabello Rodríguez
 */
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    /**
     * Endpoint para crear un nuevo juego.
     *
     * @param game El objeto Game recibido en el cuerpo de la solicitud.
     * @return Una ResponseEntity con el juego creado y la URI de su ubicación.
     */
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        return ResponseEntity.created(URI.create("/games/" + game.getId())).body(gameService.createGame(game));
    }

    /**
     * Endpoint para obtener una lista paginada de juegos.
     *
     * @param page Número de la página a obtener (por defecto es 0).
     * @param size Tamaño de la página a obtener (por defecto es 10).
     * @return Una ResponseEntity con una página de juegos.
     */
    @GetMapping
    public ResponseEntity<Page<Game>> getGames(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(gameService.getAllGames(page, size));
    }

    /**
     * Endpoint para obtener un juego específico por su ID.
     *
     * @param id El ID del juego a obtener.
     * @return Una ResponseEntity con el juego encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(gameService.getGame(id));
    }

    /**
     * Endpoint para eliminar un juego específico por su ID.
     *
     * @param id El ID del juego a eliminar.
     * @return Una ResponseEntity sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable(value = "id") String id) {
        Game game = gameService.getGame(id);
        gameService.deleteGame(game);
        return ResponseEntity.noContent().build();
    }
}

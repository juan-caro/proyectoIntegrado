package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Game;
import com.example.chesstournaments.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        return ResponseEntity.created(URI.create("/games/" + game.getId())).body(gameService.createGame(game));
    }

    @GetMapping
    public ResponseEntity<Page<Game>> getGames(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(gameService.getAllGames(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(gameService.getGame(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable(value = "id") String id) {
        Game game = gameService.getGame(id);
        gameService.deleteGame(game);
        return ResponseEntity.noContent().build();
    }
}

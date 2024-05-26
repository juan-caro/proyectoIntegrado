package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.GameRepo;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class GameService {
    private final GameRepo gameRepo;

    public Page<Game> getAllGames(int page, int size){
        return gameRepo.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    public Game getGame(String id){
        return gameRepo.findById(id).orElseThrow(() -> new RuntimeException("Game Not Found"));
    }

    public Game createGame(Game game){
        return gameRepo.save(game);
    }

    public void deleteGame(Game game){
        gameRepo.delete(game);
    }
}

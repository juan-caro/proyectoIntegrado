package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Game, String> {
    Game findByPlatformIdAndTournamentId(String platformId, String tournamentId);
}

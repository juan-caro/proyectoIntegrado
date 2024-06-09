package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepo extends JpaRepository<Tournament, String> {
    List<Tournament> findByCreatorId(String creatorId);
    Tournament findTournamentById(String tournamentId);
    List<Tournament> findTop3ByOrderByDateTimeDesc();

}

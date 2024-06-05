package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepo extends JpaRepository<Participation, String> {
    boolean existsByTournamentIdAndUserId(String tournamentId, String userId);
    void deleteByTournamentIdAndUserId(String tournamentId, String userId);
    List<Participation> findByUserId(String userId);
}
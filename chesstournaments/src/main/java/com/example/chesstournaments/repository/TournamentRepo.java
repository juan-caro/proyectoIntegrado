package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepo extends JpaRepository<Tournament, String> {
}

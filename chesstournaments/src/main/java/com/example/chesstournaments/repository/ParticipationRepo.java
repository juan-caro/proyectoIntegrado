package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepo extends JpaRepository<Participation, String> {
}
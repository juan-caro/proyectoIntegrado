package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepo extends JpaRepository<Club, String> {
    Optional<Club> findById(String id);
}

package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentRepo extends JpaRepository<Tournament, String> {
    List<Tournament> findByCreatorId(String creatorId);
    Tournament findTournamentById(String tournamentId);
    List<Tournament> findTop3ByOrderByDateTimeDesc();

    @Query("SELECT t FROM Tournament t WHERE t.state = :state ORDER BY t.dateTime DESC")
    List<Tournament> findTop3ActiveTournamentsOrderByDateTimeDesc(@Param("state") String state);

}

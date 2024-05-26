package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.TournamentRepo;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepo tournamentRepo;

    public Page<Tournament> getAllTournaments(int page, int size){
        return tournamentRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Tournament getTournament(String id){
        return tournamentRepo.findById(id).orElseThrow(() -> new RuntimeException("Tournament Not Found"));
    }

    public Tournament createTournament(Tournament tournament){
        return tournamentRepo.save(tournament);
    }

    public void deleteTournament(Tournament tournament){
        tournamentRepo.delete(tournament);
    }
}

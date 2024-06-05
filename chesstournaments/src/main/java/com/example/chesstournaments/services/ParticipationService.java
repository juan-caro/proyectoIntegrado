package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Tournament;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.TournamentRepo;
import com.example.chesstournaments.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.ParticipationRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepo participationRepo;
    private final TournamentRepo tournamentRepo;
    private final UserRepo userRepo;


    public Page<Participation> getAllParticipations(int page, int size){
        return participationRepo.findAll(PageRequest.of(page, size, Sort.by("inscription_date")));
    }

    public Participation getParticipation(String id){
        return participationRepo.findById(id).orElseThrow(() -> new RuntimeException("Participation Not Found"));
    }

    public Participation createParticipation(Participation participation){
        return participationRepo.save(participation);
    }

    public void deleteParticipation(Participation participation){
        participationRepo.delete(participation);
    }

    public Participation registerParticipation(String tournamentId, String userId) {
        // Verifica si ya existe la participación
        if (participationRepo.existsByTournamentIdAndUserId(tournamentId, userId)) {
            throw new IllegalStateException("User is already registered for the tournament");
        }

        Participation participation = new Participation();
        participation.setTournament(tournamentRepo.findTournamentById(tournamentId));
        participation.setUser(userRepo.findUserById(userId));
        participation.setInscription_date(LocalDateTime.now());
        return participationRepo.save(participation);
    }

    public void unregisterParticipation(String tournamentId, String userId) {
        participationRepo.deleteByTournamentIdAndUserId(tournamentId, userId);
    }

    public boolean isRegistered(String tournamentId, String userId) {
        System.out.println("aaaaaaaaaaa: " + participationRepo.existsByTournamentIdAndUserId(tournamentId, userId));
        return participationRepo.existsByTournamentIdAndUserId(tournamentId, userId);
    }

    public List<Tournament> getUserTournaments(String userId) {
        List<Participation> participations = participationRepo.findByUserId(userId);
        List<Tournament> tournaments = new ArrayList<>();
        for (Participation participation : participations) {
            tournaments.add(tournamentRepo.findById(participation.getTournament().getId()).orElse(null));
        }
        return tournaments;
    }
}

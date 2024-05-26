package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.ParticipationRepo;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepo participationRepo;

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
}

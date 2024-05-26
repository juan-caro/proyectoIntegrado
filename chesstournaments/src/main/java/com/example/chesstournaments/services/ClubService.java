package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.ClubRepo;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepo clubRepo;

    public Page<Club> getAllClubs(int page, int size){
        return clubRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Club getClub(String id){
        return clubRepo.findById(id).orElseThrow(() -> new RuntimeException("Club Not Found"));
    }

    public Club createClub(Club club){
        return clubRepo.save(club);
    }

    public void deleteClub(Club club){
        clubRepo.delete(club);
    }
    
}

package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.PlatformRepo;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepo platformRepo;

    public Page<Platform> getAllPlatforms(int page, int size){
        return platformRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Platform getPlatform(String id){
        return platformRepo.findById(id).orElseThrow(() -> new RuntimeException("Platform Not Found"));
    }

    public Platform createPlatform(Platform platform){
        return platformRepo.save(platform);
    }

    public void deletePlatform(Platform platform){
        platformRepo.delete(platform);
    }
}

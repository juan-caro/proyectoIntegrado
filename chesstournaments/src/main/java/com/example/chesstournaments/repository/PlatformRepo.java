package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepo extends JpaRepository<Platform, String> {
}

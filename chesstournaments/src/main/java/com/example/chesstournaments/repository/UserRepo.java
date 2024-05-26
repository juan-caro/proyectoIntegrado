package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
}

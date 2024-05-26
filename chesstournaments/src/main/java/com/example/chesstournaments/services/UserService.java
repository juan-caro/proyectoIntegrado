package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.UserRepo;

import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public Page<User> getAllUsers(int page, int size){
        return userRepo.findAll(PageRequest.of(page, size, Sort.by("username")));
    }

    public User getUser(String id){
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User getUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User createUser(User user){
        return userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public User validateUser(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


}

package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Club;
import com.example.chesstournaments.models.Tournament;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.UserRepo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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

    public String uploadPhoto(String id, MultipartFile file){
        User user = getUser(id);
        String photoUrl = photoFunction.apply(id, file);
        System.out.println("cambio foto");
        user.setPhotoUrl(photoUrl);
        System.out.println("foto cambiada a " + user.getPhotoUrl());
        userRepo.save(user);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) ->{
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get("users/image").toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())), REPLACE_EXISTING);
            System.out.println("aaaaaaaaaaaaa" + ServletUriComponentsBuilder
                    .fromCurrentContextPath().toUriString());
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/users/image/" + id + fileExtension.apply(image.getOriginalFilename())).toUriString();
        }catch (Exception e){
            throw new RuntimeException("Unable to save image");
        }
    };

    @Transactional
    public User updateUser(String id, User updatedUser) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.setElo(updatedUser.getElo());
        user.setHasChessComProfile(updatedUser.getHasChessComProfile());
        user.setChessComProfile(updatedUser.getChessComProfile());
        // Add other fields to update as needed
        return userRepo.save(user);
    }

    public List<Club> getVotedClubsByUser(String userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        return user.getVotedClubs();
    }

}

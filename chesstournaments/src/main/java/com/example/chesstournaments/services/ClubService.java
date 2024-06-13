package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Tournament;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.ClubRepo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepo clubRepo;
    private final UserRepo userRepo;

    public Page<Club> getAllClubs(int page, int size){
        return clubRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Club getClub(String id){
        return clubRepo.findById(id).orElseThrow(() -> new RuntimeException("Club Not Found"));
    }

    public Club createClub(Club club){
        return clubRepo.save(club);
    }

    public void deleteClub(Club club) {
        try {
            // Verificar si el club existe antes de eliminarlo
            Optional<Club> existingClub = clubRepo.findById(club.getId());
            if (existingClub.isPresent()) {
                clubRepo.deleteById(club.getId());
                System.out.println("Club deleted successfully: " + club);
            } else {
                System.out.println("Club not found: " + club);
            }
        } catch (Exception e) {
            System.err.println("Error deleting club: " + club);
            e.printStackTrace();
            throw new RuntimeException("Error deleting club: " + e.getMessage(), e);
        }
    }


    public String uploadPhoto(String id, MultipartFile file){
        Club club = getClub(id);
        String photoUrl = photoFunction.apply(id, file);
        club.setIconUrl(photoUrl);
        clubRepo.save(club);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) ->{
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get("clubs/image").toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())), REPLACE_EXISTING);
            System.out.println("aaaaaaaaaaaaa" + ServletUriComponentsBuilder
                    .fromCurrentContextPath().toUriString());
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/clubs/image/" + id + fileExtension.apply(image.getOriginalFilename())).toUriString();
        }catch (Exception e){
            throw new RuntimeException("Unable to save image");
        }
    };

    public void joinClub(String clubId, String userId) {
        Club club = getClub(clubId);
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!club.getMembers().contains(user)) {

            club.getMembers().add(user);
            user.setClub(club);
            System.out.println("añadido");
            clubRepo.save(club);
            userRepo.save(user);
            System.out.println("saveado");
        }
    }

    public void leaveClub(String clubId, String userId) {
        Club club = getClub(clubId);
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (club.getMembers().contains(user)) {
            club.getMembers().remove(user);
            user.setClub(null);
            clubRepo.save(club);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User is not a member of the club");
        }
    }

    public boolean isUserMemberOfClub(String clubId, String userId) {
        Club club = getClub(clubId);
        boolean matches = false;
        for (User member : club.getMembers()){
            if(member.getId().equals(userId)){
                matches = true;
            }
        }
        System.out.println("matches: " + matches);
        return matches;
    }

    @Transactional
    public void voteForClub(String clubId, String userId) {
        System.out.println("busco el club");
        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + clubId));
        System.out.println("club encontrado");
        System.out.println("busco el user");
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        System.out.println("user encontrado");

        // Aquí puedes implementar la lógica para registrar el voto del usuario en el club
        // Por ejemplo, podrías tener una lista de usuarios que han votado en el club
        // y agregar el usuario actual a esa lista

        // Ejemplo de lógica simple:
        System.out.println("rating+1");
        if (club.getRating() != null) {
            club.setRating(club.getRating() + 1L);
        }else{
            club.setRating(1L);
        }
        System.out.println("añado voter al club");
        club.getVoters().add(user);

        user.getVotedClubs().add(club);
        userRepo.save(user);
        System.out.println("voter añadido");
        // Guardar el club actualizado en la base de datos
        clubRepo.save(club);
        System.out.println("voto por el club");
    }

}

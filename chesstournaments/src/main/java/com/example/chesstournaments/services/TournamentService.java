package com.example.chesstournaments.services;

import com.example.chesstournaments.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.TournamentRepo;
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
public class TournamentService {
    private final TournamentRepo tournamentRepo;
    private final UserRepo userRepo;

    public Page<Tournament> getAllTournaments(int page, int size){
        return tournamentRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public List<Tournament> getAllTournamentsList(){
        return tournamentRepo.findAll();
    }

    public List<Tournament> getCreatorTournaments(String creatorId){
        return tournamentRepo.findByCreatorId(creatorId);
    }

    public void deleteTournament(String id){
        tournamentRepo.deleteById(id);
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

    public String uploadPhoto(String id, MultipartFile file){
        Tournament tournament = getTournament(id);
        String photoUrl = photoFunction.apply(id, file);
        tournament.setIconUrl(photoUrl);
        tournamentRepo.save(tournament);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) ->{
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get("tournaments/image").toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())), REPLACE_EXISTING);
            System.out.println("aaaaaaaaaaaaa" + ServletUriComponentsBuilder
                    .fromCurrentContextPath().toUriString());
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/tournaments/image/" + id + fileExtension.apply(image.getOriginalFilename())).toUriString();
        }catch (Exception e){
            throw new RuntimeException("Unable to save image");
        }
    };

    public List<Tournament> getTournamentsByUserId(String userId) {
        return tournamentRepo.findByCreatorId(userId);
    }
    public List<Tournament> getRecentTournaments() {
        return tournamentRepo.findTop3ByOrderByDateTimeDesc();
    }
}

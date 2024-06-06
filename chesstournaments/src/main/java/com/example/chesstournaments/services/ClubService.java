package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Tournament;
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
    
}

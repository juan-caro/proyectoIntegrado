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

/**
 * Servicio que gestiona las operaciones relacionadas con los clubes en la aplicación.
 * Proporciona métodos para crear, obtener, eliminar y manipular clubes, así como para
 * gestionar la membresía de los usuarios en los clubes y el voto por los clubes.
 * También incluye métodos para la carga y manipulación de fotos de clubes.
 *
 * Este servicio utiliza repositorios para interactuar con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Se apoya en funciones y bi-funciones para la manipulación de imágenes de clubes.
 *
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 * Requiere las dependencias ClubRepo y UserRepo para acceder a los datos de los clubes y usuarios.
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepo clubRepo;
    private final UserRepo userRepo;

    /**
     * Recupera todos los clubes paginados de la base de datos.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de clubes ordenados alfabéticamente por nombre.
     */
    public Page<Club> getAllClubs(int page, int size){
        return clubRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    /**
     * Recupera un club por su identificador único.
     *
     * @param id El identificador único del club a recuperar.
     * @return El club encontrado, si existe.
     * @throws RuntimeException Si no se encuentra ningún club con el identificador proporcionado.
     */
    public Club getClub(String id){
        return clubRepo.findById(id).orElseThrow(() -> new RuntimeException("Club Not Found"));
    }

    /**
     * Crea un nuevo club en la base de datos.
     *
     * @param club El objeto Club que representa el nuevo club a crear.
     * @return El club creado.
     */
    public Club createClub(Club club){
        return clubRepo.save(club);
    }

    /**
     * Elimina un club de la base de datos.
     *
     * @param club El club a eliminar.
     * @throws RuntimeException Si ocurre un error al eliminar el club.
     */
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

    /**
     * Carga y guarda una foto para un club específico.
     *
     * @param id   El identificador único del club para el cual se carga la foto.
     * @param file El archivo de imagen a cargar.
     * @return La URL de la foto cargada.
     */
    public String uploadPhoto(String id, MultipartFile file){
        Club club = getClub(id);
        String photoUrl = photoFunction.apply(id, file);
        club.setIconUrl(photoUrl);
        clubRepo.save(club);
        return photoUrl;
    }

    /**
     * Función que determina la extensión de archivo de una imagen.
     */
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    /**
     * Bi-función que maneja la lógica para cargar una foto de club.
     */
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

    /**
     * Permite a un usuario unirse a un club específico.
     *
     * @param clubId El identificador único del club al que el usuario desea unirse.
     * @param userId El identificador único del usuario que desea unirse al club.
     * @throws RuntimeException Si el club o el usuario no se encuentran en la base de datos.
     */
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

    /**
     * Permite a un usuario abandonar un club específico.
     *
     * @param clubId El identificador único del club del que el usuario desea salir.
     * @param userId El identificador único del usuario que desea salir del club.
     * @throws RuntimeException Si el club o el usuario no se encuentran en la base de datos,
     *                          o si el usuario no es miembro del club.
     */
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

    /**
     * Verifica si un usuario es miembro de un club específico.
     *
     * @param clubId El identificador único del club.
     * @param userId El identificador único del usuario.
     * @return true si el usuario es miembro del club, false en caso contrario.
     */
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

    /**
     * Registra el voto de un usuario por un club específico.
     *
     * @param clubId El identificador único del club.
     * @param userId El identificador único del usuario que vota por el club.
     * @throws EntityNotFoundException Si el club o el usuario no se encuentran en la base de datos.
     */
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

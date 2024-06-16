package com.example.chesstournaments.services;

import com.example.chesstournaments.exceptions.CustomNotFoundException;
import com.example.chesstournaments.models.Game;
import com.example.chesstournaments.repository.GameRepo;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Servicio que gestiona las operaciones relacionadas con los torneos de ajedrez.
 * Proporciona métodos para listar, crear, actualizar, eliminar y obtener torneos.
 *
 * Este servicio utiliza los repositorios TournamentRepo, UserRepo y GameRepo para interactuar
 * con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 *
 * Requiere las dependencias de los repositorios TournamentRepo, UserRepo y GameRepo
 * para acceder a los datos necesarios.
 *
 * Proporciona métodos para:
 * - Obtener todos los torneos paginados.
 * - Obtener todos los torneos en forma de lista.
 * - Obtener los torneos creados por un usuario específico.
 * - Eliminar un torneo por su identificador único.
 * - Obtener un torneo por su identificador único.
 * - Crear un nuevo torneo.
 * - Eliminar un torneo existente.
 * - Actualizar los detalles de un torneo existente.
 * - Subir una foto para un torneo específico.
 * - Obtener los torneos creados por un usuario específico.
 * - Obtener los torneos recientes (top 3 activos ordenados por fecha y hora descendente).
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepo tournamentRepo;
    private final UserRepo userRepo;
    private final GameRepo gameRepo;

    /**
     * Recupera todos los torneos paginados de la base de datos, ordenados por nombre.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de torneos ordenados por nombre.
     */
    public Page<Tournament> getAllTournaments(int page, int size){
        return tournamentRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    /**
     * Recupera todos los torneos de la base de datos como una lista.
     *
     * @return Una lista de todos los torneos.
     */
    public List<Tournament> getAllTournamentsList(){
        return tournamentRepo.findAll();
    }

    /**
     * Recupera los torneos creados por un usuario específico.
     *
     * @param creatorId El identificador único del creador de los torneos.
     * @return Una lista de torneos creados por el usuario especificado.
     */
    public List<Tournament> getCreatorTournaments(String creatorId){
        return tournamentRepo.findByCreatorId(creatorId);
    }

    /**
     * Elimina un torneo de la base de datos por su identificador único.
     *
     * @param id El identificador único del torneo a eliminar.
     */
    public void deleteTournament(String id){
        tournamentRepo.deleteById(id);
    }

    /**
     * Recupera un torneo por su identificador único.
     *
     * @param id El identificador único del torneo a recuperar.
     * @return El torneo encontrado, si existe.
     * @throws RuntimeException Si no se encuentra ningún torneo con el identificador proporcionado.
     */
    public Tournament getTournament(String id){
        return tournamentRepo.findById(id).orElseThrow(() -> new RuntimeException("Tournament Not Found"));
    }

    /**
     * Crea un nuevo torneo en la base de datos.
     *
     * @param tournament El objeto Tournament que representa el nuevo torneo a crear.
     * @return El torneo creado.
     */
    public Tournament createTournament(Tournament tournament){


        return tournamentRepo.save(tournament);
    }

    /**
     * Elimina un torneo existente de la base de datos.
     *
     * @param tournament El torneo a eliminar.
     */
    public void deleteTournament(Tournament tournament){
        tournamentRepo.delete(tournament);
    }

    /**
     * Sube una foto para un torneo específico y actualiza su URL de icono.
     *
     * @param id El identificador único del torneo.
     * @param file El archivo de imagen a subir.
     * @return La URL de la foto subida para el torneo.
     */
    public String uploadPhoto(String id, MultipartFile file){
        Tournament tournament = getTournament(id);
        String photoUrl = photoFunction.apply(id, file);
        tournament.setIconUrl(photoUrl);
        tournamentRepo.save(tournament);
        return photoUrl;
    }

    // Función para obtener la extensión de archivo de una imagen
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    // Función para subir una foto y obtener su URL
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

    /**
     * Recupera los torneos creados por un usuario específico.
     *
     * @param userId El identificador único del usuario creador.
     * @return Una lista de torneos creados por el usuario especificado.
     */
    public List<Tournament> getTournamentsByUserId(String userId) {
        return tournamentRepo.findByCreatorId(userId);
    }

    /**
     * Recupera los torneos recientes (top 3 activos ordenados por fecha y hora descendente).
     *
     * @return Una lista de los 3 torneos activos más recientes.
     */
    public List<Tournament> getRecentTournaments() {
        return tournamentRepo.findTop3ActiveTournamentsOrderByDateTimeDesc("Activo");
    }

    /**
     * Actualiza los detalles de un torneo existente.
     *
     * @param id El identificador único del torneo a actualizar.
     * @param name El nuevo nombre del torneo.
     * @param dateTime La nueva fecha y hora del torneo.
     * @param format El nuevo formato del torneo.
     * @param state El nuevo estado del torneo.
     * @param rounds El nuevo número de rondas del torneo.
     * @param platformId El identificador de la plataforma asociada al torneo.
     * @return El torneo actualizado.
     * @throws CustomNotFoundException Si no se encuentra ningún torneo con el identificador proporcionado.
     */
    public Tournament updateTournament(String id, String name, LocalDateTime dateTime, String format, String state, Long rounds, String platformId) {
        Tournament tournament = tournamentRepo.findById(id).orElseThrow(() -> new CustomNotFoundException("Tournament not found"));
        tournament.setName(name);
        tournament.setDateTime(dateTime);
        tournament.setFormat(format);
        tournament.setState(state);
        tournament.setRounds(rounds);
        return tournamentRepo.save(tournament);
    }
}

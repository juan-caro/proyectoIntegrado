package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Tournament;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.repository.TournamentRepo;
import com.example.chesstournaments.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.ParticipationRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Servicio que gestiona las operaciones relacionadas con las participaciones en torneos.
 * Proporciona métodos para listar, crear, eliminar y gestionar participaciones de usuarios en torneos.
 *
 * Este servicio utiliza varios repositorios (ParticipationRepo, TournamentRepo, UserRepo) para interactuar con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 *
 * Requiere las dependencias de los repositorios ParticipationRepo, TournamentRepo y UserRepo para acceder a los datos necesarios.
 *
 * Proporciona métodos para:
 * - Obtener todas las participaciones paginadas.
 * - Recuperar una participación por su identificador único.
 * - Crear una nueva participación.
 * - Eliminar una participación existente.
 * - Registrar la participación de un usuario en un torneo.
 * - Anular la participación de un usuario en un torneo.
 * - Verificar si un usuario está registrado en un torneo.
 * - Obtener todos los torneos en los que está inscrito un usuario.
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepo participationRepo;
    private final TournamentRepo tournamentRepo;
    private final UserRepo userRepo;

    /**
     * Recupera todas las participaciones paginadas de la base de datos.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de participaciones ordenadas por fecha de inscripción.
     */
    public Page<Participation> getAllParticipations(int page, int size){
        return participationRepo.findAll(PageRequest.of(page, size, Sort.by("inscription_date")));
    }

    /**
     * Recupera una participación por su identificador único.
     *
     * @param id El identificador único de la participación a recuperar.
     * @return La participación encontrada, si existe.
     * @throws RuntimeException Si no se encuentra ninguna participación con el identificador proporcionado.
     */
    public Participation getParticipation(String id){
        return participationRepo.findById(id).orElseThrow(() -> new RuntimeException("Participation Not Found"));
    }

    /**
     * Crea una nueva participación en la base de datos.
     *
     * @param participation El objeto Participation que representa la nueva participación a crear.
     * @return La participación creada.
     */
    public Participation createParticipation(Participation participation){
        return participationRepo.save(participation);
    }

    /**
     * Elimina una participación de la base de datos.
     *
     * @param participation La participación a eliminar.
     */
    public void deleteParticipation(Participation participation){
        participationRepo.delete(participation);
    }

    /**
     * Registra la participación de un usuario en un torneo.
     *
     * @param tournamentId El identificador único del torneo en el que se va a registrar la participación.
     * @param userId El identificador único del usuario que participará en el torneo.
     * @return La participación creada y registrada.
     * @throws IllegalStateException Si el usuario ya está registrado para el torneo.
     */
    public Participation registerParticipation(String tournamentId, String userId) {
        // Verifica si ya existe la participación
        if (participationRepo.existsByTournamentIdAndUserId(tournamentId, userId)) {
            throw new IllegalStateException("User is already registered for the tournament");
        }

        Participation participation = new Participation();
        participation.setTournament(tournamentRepo.findTournamentById(tournamentId));
        participation.setUser(userRepo.findUserById(userId));
        participation.setInscription_date(LocalDateTime.now());
        return participationRepo.save(participation);
    }

    /**
     * Anula la participación de un usuario en un torneo.
     *
     * @param tournamentId El identificador único del torneo del que se va a anular la participación.
     * @param userId El identificador único del usuario cuya participación se va a anular.
     */
    public void unregisterParticipation(String tournamentId, String userId) {
        participationRepo.deleteByTournamentIdAndUserId(tournamentId, userId);
    }

    /**
     * Verifica si un usuario está registrado en un torneo.
     *
     * @param tournamentId El identificador único del torneo.
     * @param userId El identificador único del usuario.
     * @return true si el usuario está registrado en el torneo, false en caso contrario.
     */
    public boolean isRegistered(String tournamentId, String userId) {
        System.out.println("aaaaaaaaaaa: " + participationRepo.existsByTournamentIdAndUserId(tournamentId, userId));
        return participationRepo.existsByTournamentIdAndUserId(tournamentId, userId);
    }

    /**
     * Recupera todos los torneos en los que está inscrito un usuario.
     *
     * @param userId El identificador único del usuario.
     * @return Una lista de torneos en los que el usuario está inscrito.
     */
    public List<Tournament> getUserTournaments(String userId) {
        List<Participation> participations = participationRepo.findByUserId(userId);
        List<Tournament> tournaments = new ArrayList<>();
        for (Participation participation : participations) {
            tournaments.add(tournamentRepo.findById(participation.getTournament().getId()).orElse(null));
        }
        return tournaments;
    }
}

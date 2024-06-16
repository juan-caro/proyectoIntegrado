package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de datos para la entidad Participation.
 * Proporciona métodos CRUD básicos y consultas personalizadas para la gestión de participaciones en torneos.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones de persistencia y consulta de datos.
 *
 * Esta interfaz se anota con @Repository para indicar que es un componente de repositorio
 * gestionado por Spring.
 *
 * @author Juan Cabello Rodríguez
 */
public interface ParticipationRepo extends JpaRepository<Participation, String> {
    /**
     * Comprueba si existe una participación en un torneo específico por el ID del torneo y el ID del usuario.
     *
     * @param tournamentId El ID del torneo.
     * @param userId       El ID del usuario.
     * @return true si existe una participación para el usuario en el torneo especificado, false de lo contrario.
     */
    boolean existsByTournamentIdAndUserId(String tournamentId, String userId);

    /**
     * Elimina una participación en un torneo específico por el ID del torneo y el ID del usuario.
     *
     * @param tournamentId El ID del torneo.
     * @param userId       El ID del usuario.
     */
    void deleteByTournamentIdAndUserId(String tournamentId, String userId);

    /**
     * Busca todas las participaciones de un usuario por su ID.
     *
     * @param userId El ID del usuario.
     * @return Una lista de todas las participaciones del usuario.
     */
    List<Participation> findByUserId(String userId);
}
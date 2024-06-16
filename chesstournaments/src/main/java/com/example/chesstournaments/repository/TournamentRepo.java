package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Repositorio de datos para la entidad Tournament.
 * Proporciona métodos CRUD básicos y consultas personalizadas para la gestión de torneos.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones de persistencia y consulta de datos.
 *
 * Esta interfaz define métodos adicionales para consultar torneos por el ID del creador, buscar un torneo por su ID específico,
 * y obtener los tres torneos más recientes ordenados por fecha y hora. También incluye una consulta personalizada para
 * obtener los tres torneos activos más recientes ordenados por fecha y hora descendente.
 *
 * @author Juan Cabello Rodríguez
 */
public interface TournamentRepo extends JpaRepository<Tournament, String> {
    /**
     * Busca torneos por el ID del creador.
     *
     * @param creatorId El ID del creador del torneo.
     * @return Una lista de torneos creados por el usuario con el ID especificado.
     */
    List<Tournament> findByCreatorId(String creatorId);

    /**
     * Busca un torneo por su ID específico.
     *
     * @param tournamentId El ID único del torneo.
     * @return El torneo encontrado, o null si no existe.
     */
    Tournament findTournamentById(String tournamentId);

    /**
     * Obtiene los tres torneos más recientes ordenados por fecha y hora descendente.
     *
     * @return Una lista de los tres torneos más recientes.
     */
    List<Tournament> findTop3ByOrderByDateTimeDesc();

    /**
     * Consulta personalizada para obtener los tres torneos activos más recientes ordenados por fecha y hora descendente.
     *
     * @param state El estado del torneo que se debe buscar (activo, inactivo, etc.).
     * @return Una lista de los tres torneos activos más recientes que coinciden con el estado especificado.
     */
    @Query("SELECT t FROM Tournament t WHERE t.state = :state ORDER BY t.dateTime DESC")
    List<Tournament> findTop3ActiveTournamentsOrderByDateTimeDesc(@Param("state") String state);

}

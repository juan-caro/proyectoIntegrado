package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de datos para la entidad Game.
 * Proporciona métodos CRUD básicos para la gestión de juegos en la base de datos.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones
 * de persistencia y consulta de datos relacionados con los juegos.
 *
 * Esta interfaz se anota con @Repository para indicar que es un componente de repositorio
 * gestionado por Spring.
 *
 * @author Juan Cabello Rodríguez
 */
public interface GameRepo extends JpaRepository<Game, String> {
    /**
     * Busca un juego por los identificadores de plataforma y torneo.
     *
     * @param platformId   El identificador único de la plataforma del juego.
     * @param tournamentId El identificador único del torneo del juego.
     * @return El juego que coincide con los identificadores de plataforma y torneo especificados,
     *         o null si no se encuentra ningún juego con esos identificadores.
     */
    Game findByPlatformIdAndTournamentId(String platformId, String tournamentId);
}

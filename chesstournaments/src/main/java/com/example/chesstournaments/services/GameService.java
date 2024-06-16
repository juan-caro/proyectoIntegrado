package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.GameRepo;

/**
 * Servicio que gestiona las operaciones relacionadas con los juegos en la aplicación.
 * Proporciona métodos para crear, obtener, eliminar y listar juegos.
 *
 * Este servicio utiliza un repositorio (GameRepo) para interactuar con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 *
 * Requiere la dependencia GameRepo para acceder a los datos de los juegos.
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class GameService {
    private final GameRepo gameRepo;

    /**
     * Recupera todos los juegos paginados de la base de datos.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de juegos ordenados por su identificador.
     */

    public Page<Game> getAllGames(int page, int size){
        return gameRepo.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    /**
     * Recupera un juego por su identificador único.
     *
     * @param id El identificador único del juego a recuperar.
     * @return El juego encontrado, si existe.
     * @throws RuntimeException Si no se encuentra ningún juego con el identificador proporcionado.
     */
    public Game getGame(String id){
        return gameRepo.findById(id).orElseThrow(() -> new RuntimeException("Game Not Found"));
    }

    /**
     * Crea un nuevo juego en la base de datos.
     *
     * @param game El objeto Game que representa el nuevo juego a crear.
     * @return El juego creado.
     */
    public Game createGame(Game game){
        return gameRepo.save(game);
    }

    /**
     * Elimina un juego de la base de datos.
     *
     * @param game El juego a eliminar.
     */
    public void deleteGame(Game game){
        gameRepo.delete(game);
    }
}

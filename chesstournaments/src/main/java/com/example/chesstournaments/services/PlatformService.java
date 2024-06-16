package com.example.chesstournaments.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.chesstournaments.models.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.PlatformRepo;

/**
 * Servicio que gestiona las operaciones relacionadas con las plataformas de juegos.
 * Proporciona métodos para listar, crear, eliminar y obtener plataformas de juegos.
 *
 * Este servicio utiliza el repositorio PlatformRepo para interactuar con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 *
 * Requiere la dependencia del repositorio PlatformRepo para acceder a los datos necesarios.
 *
 * Proporciona métodos para:
 * - Obtener todas las plataformas de juegos paginadas.
 * - Recuperar una plataforma de juego por su identificador único.
 * - Crear una nueva plataforma de juego.
 * - Eliminar una plataforma de juego existente.
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepo platformRepo;

    /**
     * Recupera todas las plataformas de juegos paginadas de la base de datos.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de plataformas de juegos ordenadas por nombre.
     */
    public Page<Platform> getAllPlatforms(int page, int size){
        return platformRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    /**
     * Recupera una plataforma de juego por su identificador único.
     *
     * @param id El identificador único de la plataforma de juego a recuperar.
     * @return La plataforma de juego encontrada, si existe.
     * @throws RuntimeException Si no se encuentra ninguna plataforma de juego con el identificador proporcionado.
     */
    public Platform getPlatform(String id){
        return platformRepo.findById(id).orElseThrow(() -> new RuntimeException("Platform Not Found"));
    }

    /**
     * Crea una nueva plataforma de juego en la base de datos.
     *
     * @param platform El objeto Platform que representa la nueva plataforma de juego a crear.
     * @return La plataforma de juego creada.
     */
    public Platform createPlatform(Platform platform){
        return platformRepo.save(platform);
    }

    /**
     * Elimina una plataforma de juego de la base de datos.
     *
     * @param platform La plataforma de juego a eliminar.
     */
    public void deletePlatform(Platform platform){
        platformRepo.delete(platform);
    }
}

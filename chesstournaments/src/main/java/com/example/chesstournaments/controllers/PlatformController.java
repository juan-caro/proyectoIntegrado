package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Platform;
import com.example.chesstournaments.models.User;
import com.example.chesstournaments.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @file PlatformController.java
 * @brief Controlador REST para manejar las plataformas de torneos de ajedrez.
 * Este controlador ofrece endpoints para crear, obtener una lista y detalles de plataformas.
 * Utiliza el servicio PlatformService para realizar operaciones con los objetos Platform.
 *
 * @autor Juan Cabello Rodríguez
 */
@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {
    private final PlatformService platformService;

    /**
     * Endpoint para crear una nueva plataforma.
     *
     * @param platform El objeto Platform a crear.
     * @return Una ResponseEntity con la Platform creada y la URI de su ubicación.
     */
    @PostMapping
    public ResponseEntity<Platform> createPlatform(@RequestBody Platform platform) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/platforms/platformID")).body(platformService.createPlatform(platform));
    }

    /**
     * Endpoint para obtener una lista paginada de plataformas.
     *
     * @param page Número de la página a obtener (por defecto es 0).
     * @param size Tamaño de la página (por defecto es 10).
     * @return Una ResponseEntity con una página de plataformas.
     */

    @GetMapping
    public ResponseEntity<Page<Platform>> getPlatforms(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("platform " + platformService.getAllPlatforms(page, size) );
        return ResponseEntity.ok().body(platformService.getAllPlatforms(page, size));
    }

    /**
     * Endpoint para obtener los detalles de una plataforma específica.
     *
     * @param id El ID de la plataforma a obtener.
     * @return Una ResponseEntity con los detalles de la plataforma solicitada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatform(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(platformService.getPlatform(id));
    }
}

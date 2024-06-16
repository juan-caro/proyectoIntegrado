package com.example.chesstournaments.controllers;

import com.example.chesstournaments.models.Club;
import lombok.RequiredArgsConstructor;
import com.example.chesstournaments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chesstournaments.services.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @file UserController.java
 * @brief Controlador REST para manejar usuarios en la aplicación de torneos de ajedrez.
 * Este controlador ofrece endpoints para crear, obtener, actualizar, eliminar y manejar fotos de usuarios.
 * Utiliza el servicio UserService para realizar operaciones con los objetos User.
 *
 * @autor Juan Cabello Rodríguez
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    public static final String PHOTO_DIRECTORY =  "C:\\Users\\Morius\\OneDrive\\Documentos\\Springboot\\chesstournaments\\users\\image\\";
    private final UserService userService;

    /**
     * Endpoint para crear un nuevo usuario.
     *
     * @param user El objeto User a crear.
     * @return Una ResponseEntity con el usuario creado y la URI de su ubicación.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/users/userID")).body(userService.createUser(user));
    }

    /**
     * Endpoint para obtener una lista paginada de usuarios.
     *
     * @param page Número de la página a obtener (por defecto es 0).
     * @param size Tamaño de la página (por defecto es 10).
     * @return Una ResponseEntity con una página de usuarios.
     */
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.getAllUsers(page, size));
    }

    /**
     * Endpoint para obtener los detalles de un usuario específico.
     *
     * @param id El ID del usuario a obtener.
     * @return Una ResponseEntity con los detalles del usuario solicitado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    /**
     * Endpoint para obtener los detalles de un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a obtener.
     * @return Una ResponseEntity con los detalles del usuario solicitado.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    /**
     * Endpoint para actualizar un usuario existente.
     *
     * @param id El ID del usuario a actualizar.
     * @param updatedUser El objeto User con los nuevos datos del usuario.
     * @return Una ResponseEntity con el usuario actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        System.out.println("updated User: " + updatedUser);
        return ResponseEntity.ok().body(userService.updateUser(id, updatedUser));
    }

    /**
     * Endpoint para subir una foto de un usuario.
     *
     * @param id   El ID del usuario.
     * @param file El archivo de la foto.
     * @return Una ResponseEntity con el mensaje de éxito.
     */
    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(userService.uploadPhoto(id, file));
    }

    /**
     * Endpoint para obtener la foto de un usuario.
     *
     * @param filename El nombre del archivo de la foto.
     * @return Un array de bytes que representa la foto.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    /**
     * Endpoint para obtener los clubes votados por un usuario específico.
     *
     * @param userId El ID del usuario.
     * @return Una ResponseEntity con una lista de clubes votados por el usuario.
     */
    @GetMapping("/{userId}/votedClubs")
    public ResponseEntity<List<Club>> getVotedClubsByUser(@PathVariable String userId) {
        List<Club> votedClubs = userService.getVotedClubsByUser(userId);
        return ResponseEntity.ok(votedClubs);
    }
}

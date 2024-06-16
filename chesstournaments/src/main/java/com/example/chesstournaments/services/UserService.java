package com.example.chesstournaments.services;

import com.example.chesstournaments.models.Club;
import com.example.chesstournaments.models.Tournament;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.chesstournaments.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.chesstournaments.repository.UserRepo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 * Proporciona métodos para listar, crear, actualizar, eliminar y obtener usuarios,
 * así como para validar credenciales de inicio de sesión y gestionar fotos de perfil.
 *
 * Este servicio utiliza el repositorio UserRepo para interactuar con la capa de persistencia de datos.
 * Está anotado con @Service para indicar que es un componente de servicio de Spring.
 * Utiliza transacciones para garantizar la consistencia de los datos durante las operaciones.
 * Utiliza logging para registrar mensajes informativos y de error durante la ejecución de métodos.
 *
 * Requiere la dependencia del repositorio UserRepo para acceder a los datos necesarios.
 *
 * Proporciona métodos para:
 * - Obtener todos los usuarios paginados, ordenados por nombre de usuario.
 * - Obtener un usuario por su identificador único.
 * - Obtener un usuario por su nombre de usuario.
 * - Crear un nuevo usuario en la base de datos.
 * - Eliminar un usuario existente de la base de datos.
 * - Validar las credenciales de inicio de sesión de un usuario.
 * - Subir una foto de perfil para un usuario específico.
 * - Actualizar detalles específicos de un usuario existente.
 * - Obtener los clubes por los que ha votado un usuario.
 *
 * @author Juan Cabello Rodríguez
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    /**
     * Recupera todos los usuarios paginados de la base de datos, ordenados por nombre de usuario.
     *
     * @param page El número de página a recuperar.
     * @param size El tamaño de la página (número de registros por página).
     * @return Una página de usuarios ordenados por nombre de usuario.
     */
    public Page<User> getAllUsers(int page, int size){
        return userRepo.findAll(PageRequest.of(page, size, Sort.by("username")));
    }

    /**
     * Recupera un usuario por su identificador único.
     *
     * @param id El identificador único del usuario a recuperar.
     * @return El usuario encontrado, si existe.
     * @throws RuntimeException Si no se encuentra ningún usuario con el identificador proporcionado.
     */
    public User getUser(String id){
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    /**
     * Recupera un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a recuperar.
     * @return El usuario encontrado, si existe.
     * @throws RuntimeException Si no se encuentra ningún usuario con el nombre de usuario proporcionado.
     */
    public User getUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param user El objeto User que representa al nuevo usuario a crear.
     * @return El usuario creado.
     */
    public User createUser(User user){
        return userRepo.save(user);
    }

    /**
     * Elimina un usuario existente de la base de datos.
     *
     * @param user El usuario a eliminar.
     */
    public void deleteUser(User user){
        userRepo.delete(user);
    }

    /**
     * Valida las credenciales de inicio de sesión de un usuario.
     *
     * @param username El nombre de usuario del usuario.
     * @param password La contraseña del usuario.
     * @return El usuario validado si las credenciales son correctas, o null si no son válidas.
     */
    public User validateUser(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Sube una foto de perfil para un usuario específico y actualiza su URL de foto.
     *
     * @param id El identificador único del usuario.
     * @param file El archivo de imagen de la foto de perfil a subir.
     * @return La URL de la foto de perfil subida para el usuario.
     */
    public String uploadPhoto(String id, MultipartFile file){
        User user = getUser(id);
        String photoUrl = photoFunction.apply(id, file);
        System.out.println("cambio foto");
        user.setPhotoUrl(photoUrl);
        System.out.println("foto cambiada a " + user.getPhotoUrl());
        userRepo.save(user);
        return photoUrl;
    }

    // Función para obtener la extensión de archivo de una imagen
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    // Función para subir una foto y obtener su URL
    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) ->{
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get("users/image").toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())), REPLACE_EXISTING);
            System.out.println("aaaaaaaaaaaaa" + ServletUriComponentsBuilder
                    .fromCurrentContextPath().toUriString());
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/users/image/" + id + fileExtension.apply(image.getOriginalFilename())).toUriString();
        }catch (Exception e){
            throw new RuntimeException("Unable to save image");
        }
    };

    /**
     * Actualiza detalles específicos de un usuario existente.
     *
     * @param id El identificador único del usuario a actualizar.
     * @param updatedUser El objeto User actualizado con los nuevos detalles.
     * @return El usuario actualizado.
     * @throws RuntimeException Si no se encuentra ningún usuario con el identificador proporcionado.
     */
    @Transactional
    public User updateUser(String id, User updatedUser) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.setElo(updatedUser.getElo());
        user.setHasChessComProfile(updatedUser.getHasChessComProfile());
        user.setChessComProfile(updatedUser.getChessComProfile());
        // Add other fields to update as needed
        return userRepo.save(user);
    }

    /**
     * Recupera los clubes por los que ha votado un usuario.
     *
     * @param userId El identificador único del usuario.
     * @return Una lista de clubes por los que ha votado el usuario.
     * @throws EntityNotFoundException Si no se encuentra ningún usuario con el identificador proporcionado.
     */
    public List<Club> getVotedClubsByUser(String userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        return user.getVotedClubs();
    }

}

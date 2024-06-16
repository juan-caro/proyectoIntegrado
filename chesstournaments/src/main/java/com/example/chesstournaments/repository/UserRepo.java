package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repositorio de datos para la entidad User.
 * Proporciona métodos CRUD básicos y consultas personalizadas para la gestión de usuarios.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones de persistencia y consulta de datos.
 *
 * Esta interfaz define métodos adicionales para buscar usuarios por su ID y por su nombre de usuario.
 * También incluye un método para encontrar un usuario específico por su ID.
 *
 * @author Juan Cabello Rodríguez
 */
@Repository
public interface UserRepo extends JpaRepository<User, String> {

    /**
     * Busca un usuario por su ID único.
     *
     * @param id El ID único del usuario.
     * @return Un Optional que puede contener el usuario encontrado, o empty si no existe.
     */
    Optional<User> findById(String id);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario.
     * @return Un Optional que puede contener el usuario encontrado, o empty si no existe.
     */
    Optional<User> findByUsername(String username);

    /**
     * Encuentra un usuario por su ID específico.
     *
     * @param id El ID único del usuario.
     * @return El usuario encontrado, o null si no existe.
     */
    User findUserById(String id);
}

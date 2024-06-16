package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de datos para la entidad Club.
 * Proporciona métodos CRUD básicos para la gestión de clubes en la base de datos.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones
 * de persistencia y consulta de datos relacionados con los clubes.
 *
 * Esta interfaz se anota con @Repository para indicar que es un componente de repositorio
 * gestionado por Spring.
 *
 * @author Juan Cabello Rodríguez
 */
@Repository
public interface ClubRepo extends JpaRepository<Club, String> {
    /**
     * Busca un club por su identificador único.
     *
     * @param id El identificador único del club a buscar.
     * @return Un Optional que puede contener un Club si se encuentra en la base de datos,
     *         o un Optional vacío si no se encuentra.
     */
    Optional<Club> findById(String id);
}

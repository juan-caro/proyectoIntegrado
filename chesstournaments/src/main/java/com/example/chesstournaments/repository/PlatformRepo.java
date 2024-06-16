package com.example.chesstournaments.repository;

import com.example.chesstournaments.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio de datos para la entidad Platform.
 * Proporciona métodos CRUD básicos para la gestión de plataformas de torneos.
 * Utiliza Spring Data JPA y extiende JpaRepository para facilitar las operaciones de persistencia y consulta de datos.
 *
 * Esta interfaz no define métodos adicionales más allá de los proporcionados por JpaRepository,
 * ya que hereda todos los métodos de consulta básicos y de manipulación de entidades de Spring Data JPA.
 *
 * @author Juan Cabello Rodríguez
 */
public interface PlatformRepo extends JpaRepository<Platform, String> {
}

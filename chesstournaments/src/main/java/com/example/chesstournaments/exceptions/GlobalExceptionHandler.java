package com.example.chesstournaments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.NoSuchFileException;

/**
 * @file GlobalExceptionHandler.java
 * @brief Clase para manejar excepciones globales en la aplicación.
 *
 * Esta clase utiliza Spring's RestControllerAdvice para manejar excepciones globales
 * que pueden ocurrir durante el procesamiento de las peticiones HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja NoSuchFileException cuando un archivo solicitado no se encuentra.
     *
     * @param e Excepción NoSuchFileException lanzada.
     * @return ResponseEntity con estado HTTP 404 (NOT FOUND) y mensaje de error detallado.
     */
    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<String> handleNoSuchFileException(NoSuchFileException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + e.getFile());
    }

    /**
     * Maneja Exception para cualquier otro tipo de excepción no esperada.
     *
     * @param e Excepción genérica lanzada.
     * @return ResponseEntity con estado HTTP 500 (INTERNAL SERVER ERROR) y mensaje de error genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}

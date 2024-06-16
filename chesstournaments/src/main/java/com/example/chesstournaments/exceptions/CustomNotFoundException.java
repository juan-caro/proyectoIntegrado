package com.example.chesstournaments.exceptions;

/**
 * @file CustomNotFoundException.java
 * @brief Excepción personalizada para indicar que un recurso no fue encontrado.
 *
 * Esta excepción se utiliza cuando un recurso específico no puede ser encontrado en la aplicación.
 */
public class CustomNotFoundException extends RuntimeException {
    /**
     * Constructor que acepta un mensaje para la excepción.
     *
     * @param message Mensaje que describe la razón de la excepción.
     */
    public CustomNotFoundException(String message) {
        super(message);
    }
}

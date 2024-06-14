package com.example.chesstournaments.controllers;

/**
 * @file AuthController.java
 * @brief Controlador para autenticación de usuarios.
 *
 * Este archivo define el controlador para manejar las solicitudes de autenticación
 * de los usuarios en la aplicación de torneos de ajedrez.
 *
 * @autor Juan Cabello Rodriguez
 */

import com.example.chesstournaments.models.User;
import com.example.chesstournaments.requests.UserLoginRequest;
import com.example.chesstournaments.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @brief Controlador de autenticación.
 *
 * Este controlador maneja las solicitudes de inicio de sesión de los usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    /**
     * @brief Maneja la solicitud de inicio de sesión.
     *
     * Este método valida las credenciales del usuario y devuelve una respuesta adecuada.
     *
     * @param loginRequest Objeto que contiene el nombre de usuario y la contraseña.
     * @return ResponseEntity con el usuario autenticado o un mensaje de error si las credenciales son inválidas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            System.out.println("no nulo");
            return ResponseEntity.ok(user);
        } else {
            System.out.println("nulo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}


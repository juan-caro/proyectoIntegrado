package com.example.chesstournaments.controllers;
/**
 * @file CustomErrorController.java
 * @brief Controlador de manejo de errores personalizados.
 *
 * Este archivo define el controlador para manejar errores personalizados en la aplicación de torneos de ajedrez.
 *
 * @autor Juan Cabello Rodriguez
 */
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import java.util.Map;
/**
 * @brief Controlador de errores personalizados.
 *
 * Este controlador maneja las respuestas de error personalizadas.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * @brief Maneja los errores de la aplicación.
     *
     * @param webRequest El objeto de la solicitud web que contiene detalles del error.
     * @return ResponseEntity con los detalles del error y el código de estado HTTP 500 (Internal Server Error).
     */
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(WebRequest webRequest) {
        DefaultErrorAttributes errorAttributes = new DefaultErrorAttributes();
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorDetails.put("message", "Something went wrong");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

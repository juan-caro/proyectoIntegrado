package com.example.chesstournaments.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @file CorsConfig.java
 * @brief Configuración de CORS para la aplicación de torneos de ajedrez.
 * Este archivo configura las políticas de CORS para permitir solicitudes desde cualquier origen.
 *
 * @autor Juan Cabello Rodríguez
 */
@Configuration
public class CorsConfig {
    /**
     * Configura el bean para manejar la configuración de CORS.
     *
     * @return Un WebMvcConfigurer con las configuraciones de CORS especificadas.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configura los mapeos de CORS.
             *
             * @param registry El registro de mapeos de CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}

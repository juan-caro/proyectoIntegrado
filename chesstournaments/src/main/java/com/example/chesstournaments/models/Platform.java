package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

/**
 * Representa una plataforma de juegos utilizada para organizar torneos.
 * Cada plataforma puede tener múltiples juegos asociados.
 *
 * Esta clase es una entidad JPA que se mapea a la tabla "platforms" en la base de datos.
 * Utiliza Lombok para la generación de getters, setters, constructores y métodos equals/hashCode.
 *
 * Las anotaciones de Jackson se utilizan para personalizar la serialización JSON.
 *
 * @author Juan Cabello Rodríguez
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "platforms")
public class Platform {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "face_to_face")
    private Boolean faceToFace;
    @Column(name = "platform_url")
    private String platformUrl;

    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL)
    @JsonBackReference("platform-games")
    private List<Game> games;
}

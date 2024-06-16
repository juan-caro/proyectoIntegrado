package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa un torneo de ajedrez con detalles como nombre, fecha y hora, formato, estado, rondas y URL de icono.
 * Cada torneo puede tener varios participantes y juegos asociados.
 *
 * Esta clase es una entidad JPA que se mapea a la tabla "tournaments" en la base de datos.
 * Utiliza Lombok para la generación de getters, setters, constructores y métodos equals/hashCode.
 *
 * Las anotaciones de Jackson se utilizan para personalizar la serialización JSON.
 *
 * @author Juan Cabello Rodríguez
 */
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "tournaments")
public class Tournament {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "format")
    private String format;
    @Column(name = "state")
    private String state;
    @Column(name = "rounds")
    private Long rounds;
    @Column(name = "iconUrl")
    private String iconUrl;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonBackReference
    @Builder.Default
    private List<Participation> participations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference("user-createdTournaments")
    private User creator;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonBackReference("tournament-games")
    private List<Game> games;

    /**
     * Método que devuelve el número de participantes en este torneo.
     * Este método se utiliza para agregar dinámicamente una propiedad "participantCount" en la serialización JSON.
     *
     * @return Número de participantes en el torneo.
     */
   public int getParticipantCount() {
        int numParticipations = 0;
        if(participations.size()>=1){
            numParticipations = participations.size();
        }
        return numParticipations;
    }

    /**
     * Método getter adicional para devolver propiedades adicionales en la serialización JSON.
     * En este caso, agrega dinámicamente la propiedad "participantCount" con el resultado de getParticipantCount().
     *
     * @return Mapa de propiedades adicionales para la serialización JSON.
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        Map<String, Object> additionalProperties = new HashMap<>();
        additionalProperties.put("participantCount", getParticipantCount());
        return additionalProperties;
    }
}

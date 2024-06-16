package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
/**
 * Representa un usuario en el sistema de gestión de torneos de ajedrez.
 * Cada usuario tiene un identificador único, nombre de usuario, contraseña,
 * dirección de correo electrónico, Elo (puntuación en ajedrez), URL de foto,
 * indicador de perfil en Chess.com, perfil de Chess.com y relaciones con otros
 * objetos como clubes, torneos creados, participaciones y clubes votados.
 *
 * Esta clase es una entidad JPA que se mapea a la tabla "users" en la base de datos.
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
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", unique = false, nullable = false)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Builder.Default
    @Column(name = "elo", unique = false)
    private Long elo = 0L;
    @Builder.Default
    @Column(name = "photoUrl")
    private String photoUrl = "http://localhost:8080/users/image/default.jpg";
    @Builder.Default
    @Column(name = "has_chess_com_profile")
    private Boolean hasChessComProfile = false;
    @Column(name = "chess_com_profile")
    private String chessComProfile;

    // Constructor para establecer el valor por defecto de photoUrl
    /*public User() {
        this.photoUrl = "http://localhost:8080/users/image/default.jpg"; // Cambia "default_url" al valor por defecto que desees
    }*/

    /**
     * Relación muchos a uno con la clase Club.
     * Club al que pertenece este usuario.
     * Se utiliza JsonBackReference para evitar ciclos de referencia en la serialización JSON.
     */
    @ManyToOne
    @JoinColumn(name = "club_id")
    @JsonBackReference("user-club")
    private Club club;

    /**
     * Relación uno a uno con la clase Club.
     * Club que fue creado por este usuario.
     * Se utiliza JsonManagedReference para evitar ciclos de referencia en la serialización JSON.
     */
    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonManagedReference("user-createdClub")
    @JsonIgnore
    private Club createdClub;

    /**
     * Relación uno a muchos con la clase Participation.
     * Lista de participaciones asociadas a este usuario.
     * Se utiliza mappedBy para referenciar el atributo "user" en la clase Participation.
     */
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-participations")
    private List<Participation> participations;

    /**
     * Relación uno a muchos con la clase Tournament.
     * Lista de torneos creados por este usuario.
     * Se utiliza mappedBy para referenciar el atributo "creator" en la clase Tournament.
     */
    @OneToMany(mappedBy = "creator")
    @JsonManagedReference("user-createdTournaments")
    private List<Tournament> createdTournaments;

    /**
     * Relación muchos a muchos con la clase Club.
     * Lista de clubes a los que este usuario ha votado.
     * Se utiliza la tabla intermedia "user_voted_clubs" para gestionar la relación.
     * Se utiliza JsonBackReference para evitar ciclos de referencia en la serialización JSON.
     */
    @ManyToMany
    @JoinTable(
            name = "user_voted_clubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    @JsonBackReference("user-votedClub")
    private List<Club> votedClubs;

    /**
     * Método toString() que imprime una representación de cadena del usuario.
     * Esta representación incluye el id, nombre de usuario, correo electrónico, Elo,
     * indicador de perfil en Chess.com, perfil de Chess.com y URL de foto.
     *
     * @return Representación de cadena del objeto User.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", elo=" + elo +
                ", hasChessComProfile=" + hasChessComProfile +
                ", chessComProfile='" + chessComProfile + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }



}

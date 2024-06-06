package com.example.chesstournaments.models;
/**
 * @author: Juan Cabello Rodríguez
 * */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
/**
 * @author: Juan Cabello Rodríguez
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    @Column(name = "elo", unique = false)
    private Long elo;
    @Builder.Default
    @Column(name = "photoUrl")
    private String photoUrl = "http://localhost:8080/users/image/default.jpg";

    // Constructor para establecer el valor por defecto de photoUrl
    public User() {
        this.photoUrl = ""; // Cambia "default_url" al valor por defecto que desees
    }

    //TODO: Incluir las relaciones.

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonManagedReference("user-createdClub")
    private Club createdClub;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-participations")
    private List<Participation> participations;
    @OneToMany(mappedBy = "creator")
    @JsonManagedReference("user-createdTournaments")
    private List<Tournament> createdTournaments;



}

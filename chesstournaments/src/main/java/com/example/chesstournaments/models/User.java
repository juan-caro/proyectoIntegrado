package com.example.chesstournaments.models;
/**
 * @author: Juan Cabello Rodríguez
 * */
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //TODO: Incluir las relaciones.

    @ManyToOne
    @JoinColumn(name = "club_id")
    @JsonBackReference("user-club")

    private Club club;
    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonManagedReference("user-createdClub")
    @JsonIgnore
    private Club createdClub;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-participations")
    private List<Participation> participations;
    @OneToMany(mappedBy = "creator")
    @JsonManagedReference("user-createdTournaments")
    private List<Tournament> createdTournaments;

    @ManyToMany
    @JoinTable(
            name = "user_voted_clubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    @JsonBackReference("user-votedClub")
    private List<Club> votedClubs;


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

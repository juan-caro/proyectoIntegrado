package com.example.chesstournaments.models;
/**
 * @author: Juan Cabello Rodríguez
 * */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Column(name = "photoUrl")
    private String photoUrl;

    //TODO: Incluir las relaciones.

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    private Club createdClub;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Participation> participations;
    @OneToMany(mappedBy = "creator")
    @JsonManagedReference
    private List<Tournament> createdTournaments;



}

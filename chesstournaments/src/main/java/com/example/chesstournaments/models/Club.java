package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
/**
 * @file Club.java
 * @brief Clase que representa un club en el sistema.
 *
 * Esta clase define la estructura de un club, incluyendo sus miembros, votantes,
 * y el usuario creador del club.
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
@Table(name = "clubs")
public class Club {

    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    @Builder.Default
    private Long rating = 0L;
    @Column(name = "iconUrl")
    private String iconUrl;

    @OneToMany(mappedBy = "club", cascade = CascadeType.DETACH, orphanRemoval = true)
    @JsonManagedReference("user-club")
    private List<User> members;

    @ManyToMany(mappedBy = "votedClubs")
    @JsonBackReference("user-votedClub")
    private List<User> voters = new ArrayList<>();

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference("user-createdClub")
    private User creator;

    /**
     * Método transitorio para obtener el número de miembros del club.
     *
     * @return Número de miembros del club.
     */
    @Transient
    @JsonProperty("membersCount")
    public int getMembersCount() {
        return members != null ? members.size() : 0;
    }

    /**
     * Override del método toString para representar el objeto Club como una cadena.
     *
     * @return Representación en cadena del objeto Club.
     */
    @Override
    public String toString() {
        return "Club{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", iconUrl='" + iconUrl + '\'' +
                ", members=" + members + // esto imprimirá una lista de usuarios, considera cómo quieres que se impriman los usuarios
                ", creator=" + (creator != null ? creator.getUsername() : "null") + // suponer que User tiene un método getUsername()
                '}';
    }



}

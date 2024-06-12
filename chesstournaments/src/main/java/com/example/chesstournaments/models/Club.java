package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
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
    private Long rating;
    @Column(name = "iconUrl")
    private String iconUrl;

    @OneToMany(mappedBy = "club", cascade = CascadeType.DETACH, orphanRemoval = true)
    @JsonManagedReference("user-club")
    private List<User> members = new ArrayList<>();

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference("user-createdClub")
    private User creator;

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

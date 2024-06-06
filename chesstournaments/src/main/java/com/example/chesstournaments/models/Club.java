package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @OneToMany(mappedBy = "club")
    private List<User> members = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference("user-createdClub")
    private User creator;
}

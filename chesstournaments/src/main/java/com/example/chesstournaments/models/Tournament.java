package com.example.chesstournaments.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
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
    private List<Participation> participations;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference("user-createdTournaments")
    private User creator;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonBackReference("tournament-games")
    private List<Game> games;
}

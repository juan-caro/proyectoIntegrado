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

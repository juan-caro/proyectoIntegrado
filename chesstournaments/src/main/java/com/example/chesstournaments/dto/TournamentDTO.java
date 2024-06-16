package com.example.chesstournaments.dto;

import lombok.*;

import java.time.LocalDateTime;
/**
 * @file TournamentDTO.java
 * @brief DTO (Data Transfer Object) para representar los datos de un torneo de ajedrez.
 *
 * Este objeto se utiliza para transferir datos relacionados con un torneo entre el cliente y el servidor.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {
    private String name;
    private LocalDateTime dateTime;
    private String format;
    private String state;
    private Long rounds;
    private String plataformId;
}

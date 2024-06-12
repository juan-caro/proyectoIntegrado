package com.example.chesstournaments.dto;

import lombok.*;

import java.time.LocalDateTime;

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

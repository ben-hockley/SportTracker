package com.SportTracker.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private Long id;
    private String name;
    private Long teamId;
    private String position; // Positions referred tp by their abbreviations (e.g. QB, RB, WR, TE, OL, DL, LB, CB, S, K, P, ATH)
    private Integer number;

    private Integer recruitYear;
    private Integer recruitStars;
    private Integer draftYear;
    private Integer draftPick;
}

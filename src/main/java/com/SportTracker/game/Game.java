package com.SportTracker.game;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    private Long id;

    // required fields
    private Date date;
    private Integer homeTeamId;
    private Integer awayTeamId;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

    private Long seasonId;
    // optional fields
    private Integer homeTeamScoreQ1;
    private Integer homeTeamScoreQ2;
    private Integer homeTeamScoreQ3;
    private Integer homeTeamScoreQ4;
    private Integer awayTeamScoreQ1;
    private Integer awayTeamScoreQ2;
    private Integer awayTeamScoreQ3;
    private Integer awayTeamScoreQ4;

    private String homePassing;
    private String awayPassing;
    private String homeRushing;
    private String awayRushing;
    private String homeReceiving;
    private String awayReceiving;
}

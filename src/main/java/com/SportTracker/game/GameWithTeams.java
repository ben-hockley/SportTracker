package com.SportTracker.game;

import com.SportTracker.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class GameWithTeams {
    private Long Id;
    private Date date;
    private Team homeTeam;
    private Team awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

    public GameWithTeams(Game gameToConvert, Team homeTeam, Team awayTeam) {
        this.Id = gameToConvert.getId();
        this.date = gameToConvert.getDate();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = gameToConvert.getHomeTeamScore();
        this.awayTeamScore = gameToConvert.getAwayTeamScore();
    }
}

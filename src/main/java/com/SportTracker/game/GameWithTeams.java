package com.SportTracker.game;

import com.SportTracker.team.Team;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class GameWithTeams {
    private Long Id;

    // required fields
    private Date date;
    private Team homeTeam;
    private Team awayTeam;
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

    public GameWithTeams(Game gameToConvert, Team homeTeam, Team awayTeam) {
        this.Id = gameToConvert.getId();

        this.date = gameToConvert.getDate();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = gameToConvert.getHomeTeamScore();
        this.awayTeamScore = gameToConvert.getAwayTeamScore();

        this.seasonId = gameToConvert.getSeasonId();

        this.homeTeamScoreQ1 = gameToConvert.getHomeTeamScoreQ1();
        this.homeTeamScoreQ2 = gameToConvert.getHomeTeamScoreQ2();
        this.homeTeamScoreQ3 = gameToConvert.getHomeTeamScoreQ3();
        this.homeTeamScoreQ4 = gameToConvert.getHomeTeamScoreQ4();
        this.awayTeamScoreQ1 = gameToConvert.getAwayTeamScoreQ1();
        this.awayTeamScoreQ2 = gameToConvert.getAwayTeamScoreQ2();
        this.awayTeamScoreQ3 = gameToConvert.getAwayTeamScoreQ3();
        this.awayTeamScoreQ4 = gameToConvert.getAwayTeamScoreQ4();

        this.homePassing = gameToConvert.getHomePassing();
        this.awayPassing = gameToConvert.getAwayPassing();
        this.homeRushing = gameToConvert.getHomeRushing();
        this.awayRushing = gameToConvert.getAwayRushing();
        this.homeReceiving = gameToConvert.getHomeReceiving();
        this.awayReceiving = gameToConvert.getAwayReceiving();
    }
}

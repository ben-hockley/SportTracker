package com.SportTracker.game;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class GameRepository {
    private final JdbcClient jdbcClient;

    public GameRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Game> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.game")
                .query(Game.class)
                .list();
    }

    public Game findById(Long id) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.game WHERE id = :id")
                .param("id", id)
                .query(Game.class)
                .single();
    }

    public void save(Game game) {

        Date gameDate = game.getDate();
        String homeTeamName = game.getHomeTeamName();
        String awayTeamName = game.getAwayTeamName();
        Integer homeTeamScore = game.getHomeTeamScore();
        Integer awayTeamScore = game.getAwayTeamScore();

        jdbcClient.sql("INSERT INTO sport_tracker.game (date, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore) VALUES (:date, :homeTeamName, :awayTeamName, :homeTeamScore, :awayTeamScore)")
                .param("date", gameDate)
                .param("homeTeamName", homeTeamName)
                .param("awayTeamName", awayTeamName)
                .param("homeTeamScore", homeTeamScore)
                .param("awayTeamScore", awayTeamScore)
                .update();
    }
}

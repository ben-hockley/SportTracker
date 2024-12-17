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

    public List<Game> findByTeamId(Long teamId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.game WHERE homeTeamId = :teamId OR awayTeamId = :teamId")
                .param("teamId", teamId)
                .query(Game.class)
                .list();
    }

    public void save(Game game) {

        Date gameDate = game.getDate();
        Integer homeTeamId = game.getHomeTeamId();
        Integer awayTeamId = game.getAwayTeamId();
        Integer homeTeamScore = game.getHomeTeamScore();
        Integer awayTeamScore = game.getAwayTeamScore();

        jdbcClient.sql("INSERT INTO sport_tracker.game (date, homeTeamId, awayTeamId, homeTeamScore, awayTeamScore) VALUES (:date, :homeTeamId, :awayTeamId, :homeTeamScore, :awayTeamScore)")
                .param("date", gameDate)
                .param("homeTeamId", homeTeamId)
                .param("awayTeamId", awayTeamId)
                .param("homeTeamScore", homeTeamScore)
                .param("awayTeamScore", awayTeamScore)
                .update();
    }
}

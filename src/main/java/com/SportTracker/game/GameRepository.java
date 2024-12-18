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

        // Compulsory fields
        Date gameDate = game.getDate();
        Integer homeTeamId = game.getHomeTeamId();
        Integer awayTeamId = game.getAwayTeamId();
        Integer homeTeamScore = game.getHomeTeamScore();
        Integer awayTeamScore = game.getAwayTeamScore();

        // Optional fields
        Integer homeTeamScoreQ1 = game.getHomeTeamScoreQ1();
        Integer homeTeamScoreQ2 = game.getHomeTeamScoreQ2();
        Integer homeTeamScoreQ3 = game.getHomeTeamScoreQ3();
        Integer homeTeamScoreQ4 = game.getHomeTeamScoreQ4();
        Integer awayTeamScoreQ1 = game.getAwayTeamScoreQ1();
        Integer awayTeamScoreQ2 = game.getAwayTeamScoreQ2();
        Integer awayTeamScoreQ3 = game.getAwayTeamScoreQ3();
        Integer awayTeamScoreQ4 = game.getAwayTeamScoreQ4();

        jdbcClient.sql(
                "INSERT INTO sport_tracker.game (date,homeTeamId, awayTeamId, homeTeamScore, awayTeamScore, homeTeamScoreQ1, homeTeamScoreQ2, homeTeamScoreQ3, homeTeamScoreQ4, awayTeamScoreQ1, awayTeamScoreQ2, awayTeamScoreQ3, awayTeamScoreQ4) VALUES (:date, :homeTeamId, :awayTeamId, :homeTeamScore, :awayTeamScore, :homeTeamScoreQ1, :homeTeamScoreQ2, :homeTeamScoreQ3, :homeTeamScoreQ4, :awayTeamScoreQ1, :awayTeamScoreQ2, :awayTeamScoreQ3, :awayTeamScoreQ4)")
                .param("date", gameDate)
                .param("homeTeamId", homeTeamId)
                .param("awayTeamId", awayTeamId)
                .param("homeTeamScore", homeTeamScore)
                .param("awayTeamScore", awayTeamScore)
                .param("homeTeamScoreQ1", homeTeamScoreQ1)
                .param("homeTeamScoreQ2", homeTeamScoreQ2)
                .param("homeTeamScoreQ3", homeTeamScoreQ3)
                .param("homeTeamScoreQ4", homeTeamScoreQ4)
                .param("awayTeamScoreQ1", awayTeamScoreQ1)
                .param("awayTeamScoreQ2", awayTeamScoreQ2)
                .param("awayTeamScoreQ3", awayTeamScoreQ3)
                .param("awayTeamScoreQ4", awayTeamScoreQ4)

                .update();
    }
}

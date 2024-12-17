package com.SportTracker.player;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {
    private final JdbcClient jdbcClient;

    public PlayerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Player> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.player")
                .query(Player.class)
                .list();
    }

    public Player findById(Long id) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.player WHERE id = :id")
                .param("id", id)
                .query(Player.class)
                .single();
    }

    public void save(Player player) {
        String playerName = player.getName();
        Integer playerNumber = player.getNumber();
        String playerPosition = player.getPosition();
        Long playerTeamId = player.getTeamId();

        jdbcClient.sql("INSERT INTO sport_tracker.player (name, number, position, teamId) VALUES (:name, :number, :position, :teamId)")
                .param("name", playerName)
                .param("number", playerNumber)
                .param("position", playerPosition)
                .param("teamId", playerTeamId)
                .update();
    }
}

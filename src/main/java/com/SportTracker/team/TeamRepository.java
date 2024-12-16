package com.SportTracker.team;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepository {
    private final JdbcClient jdbcClient;

    public TeamRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Team> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.team")
                .query(Team.class)
                .list();
    }

    public Team findById(Long id) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.team WHERE id = :id")
                .param("id", id)
                .query(Team.class)
                .single();
    }

    public void save(Team team) {

        String teamName = team.getName();
        String teamLeague = team.getLeague();
        String teamLogoUrl = team.getLogoUrl();

        jdbcClient.sql("INSERT INTO sport_tracker.team (name, league, logoUrl) VALUES (:name, :league, :logoUrl)")
                .param("name", teamName)
                .param("league", teamLeague)
                .param("logoUrl", teamLogoUrl)
                .update();
    }
}

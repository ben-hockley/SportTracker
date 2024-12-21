package com.SportTracker.league;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeagueRepository {
    private final JdbcClient jdbcClient;

    public LeagueRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<League> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.league")
                .query(League.class)
                .list();
    }

    public League findById(Long id) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.league WHERE id = :id")
                .param("id", id)
                .query(League.class)
                .single();
    }
}

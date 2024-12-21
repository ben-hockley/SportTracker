package com.SportTracker.season;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeasonRepository {
    private final JdbcClient jdbcClient;

    public SeasonRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Season> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.season")
                .query(Season.class)
                .list();
    }

    public Season findById(Long id) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.season WHERE id = :id")
                .param("id", id)
                .query(Season.class)
                .single();
    }

    public List<Season> findByLeagueId(Long leagueId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.season WHERE leagueId = :leagueId")
                .param("leagueId", leagueId)
                .query(Season.class)
                .list();
    }
}

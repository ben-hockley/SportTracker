package com.SportTracker.stats;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RushingRepository {
    private final JdbcClient jdbcClient;

    public RushingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Rushing> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.rushingstats")
                .query(Rushing.class)
                .list();
    }

    public List<Rushing> findByGameIdAndHomeOrAway(Long gameId, String homeOrAway) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.rushingstats WHERE gameId = :gameId AND homeOrAway = :homeOrAway")
                .param("gameId", gameId)
                .param("homeOrAway", homeOrAway)
                .query(Rushing.class)
                .list();
    }
}

package com.SportTracker.stats;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReceivingRepository {
    private final JdbcClient jdbcClient;

    public ReceivingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Receiving> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.receivingstats")
                .query(Receiving.class)
                .list();
    }

    public List<Receiving> findByGameIdAndHomeOrAway(Long gameId, String homeOrAway) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.receivingstats WHERE gameId = :gameId AND homeOrAway = :homeOrAway")
                .param("gameId", gameId)
                .param("homeOrAway", homeOrAway)
                .query(Receiving.class)
                .list();
    }
}

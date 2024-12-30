package com.SportTracker.stats;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PassingRepository {
    private final JdbcClient jdbcClient;

    public PassingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Passing> findAll() {
        return jdbcClient.sql("SELECT * FROM sport_tracker.passingstats")
                .query(Passing.class)
                .list();
    }

    public List<Passing> findByGameIdAndHomeOrAway(Long gameId, String homeOrAway) {
        List<Passing> passingStats =
                jdbcClient.sql("SELECT * FROM sport_tracker.passingstats WHERE gameId = :gameId AND homeOrAway = :homeOrAway")
                .param("gameId", gameId)
                .param("homeOrAway", homeOrAway)
                .query(Passing.class)
                .list();

        // sort passing stats in descending order by yards
        passingStats.sort((a, b) -> b.getYards() - a.getYards());
        return passingStats;
    }

    public List<Passing> getPassingLeadersBySeason(Long seasonId) {
        return jdbcClient.sql("SELECT playerId, " +
                        "seasonId, " +
                        "playerName, " +
                        "SUM(yards) as yards, " +
                        "SUM(attempts) as attempts, " +
                        "SUM(completions) as completions, " +
                        "SUM(touchdowns) as touchdowns, " +
                        "SUM(interceptions) as interceptions " +
                        "FROM sport_tracker.passingstats " +
                        "WHERE seasonId = :seasonId " +
                        "GROUP BY playerId " +
                        "ORDER BY yards DESC")
                .param("seasonId", seasonId)
                .query(Passing.class)
                .list();
    }

    public List<Passing> findByPlayerId(Long playerId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.passingstats WHERE playerId = :playerId")
                .param("playerId", playerId)
                .query(Passing.class)
                .list();
    }

}

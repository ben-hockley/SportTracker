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
        List<Rushing> rushingStats =
                jdbcClient.sql("SELECT * FROM sport_tracker.rushingstats WHERE gameId = :gameId AND homeOrAway = :homeOrAway")
                .param("gameId", gameId)
                .param("homeOrAway", homeOrAway)
                .query(Rushing.class)
                .list();

        // sort rushing stats in descending order by yards
        rushingStats.sort((a, b) -> b.getYards() - a.getYards());
        return rushingStats;
    }

    public List<Rushing> getRushingLeadersBySeason(Long seasonId) {
        return jdbcClient.sql("SELECT playerId, " +
                        "seasonId, " +
                        "playerName, " +
                        "SUM(yards) as yards, " +
                        "SUM(attempts) as attempts, " +
                        "SUM(touchdowns) as touchdowns, " +
                        "MAX(longest) as longest " +
                        "FROM sport_tracker.rushingstats " +
                        "WHERE seasonId = :seasonId " +
                        "GROUP BY playerId " +
                        "ORDER BY yards DESC")
                .param("seasonId", seasonId)
                .query(Rushing.class)
                .list();
    }

    public List<Rushing> findByPlayerId(Long playerId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.rushingstats WHERE playerId = :playerId")
                .param("playerId", playerId)
                .query(Rushing.class)
                .list();
    }
}

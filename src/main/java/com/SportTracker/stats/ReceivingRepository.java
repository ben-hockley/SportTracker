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
        List<Receiving> receivingStats =
                jdbcClient.sql("SELECT * FROM sport_tracker.receivingstats WHERE gameId = :gameId AND homeOrAway = :homeOrAway")
                        .param("gameId", gameId)
                        .param("homeOrAway", homeOrAway)
                        .query(Receiving.class)
                        .list();

        // sort receiving stats in descending order by yards
        receivingStats.sort((a, b) -> b.getYards() - a.getYards());
        return receivingStats;
    }

    public List<Receiving> getReceivingLeadersBySeason(Long seasonId) {
        return jdbcClient.sql("SELECT playerId, " +
                        "seasonId, " +
                        "playerName, " +
                        "SUM(yards) as yards, " +
                        "SUM(receptions) as receptions, " +
                        "SUM(touchdowns) as touchdowns, " +
                        "MAX(longest) as longest " +
                        "FROM sport_tracker.receivingstats " +
                        "WHERE seasonId = :seasonId " +
                        "GROUP BY playerId " +
                        "ORDER BY yards DESC")
                .param("seasonId", seasonId)
                .query(Receiving.class)
                .list();
    }

    public List<Receiving> findByPlayerId(Long playerId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.receivingstats WHERE playerId = :playerId")
                .param("playerId", playerId)
                .query(Receiving.class)
                .list();
    }

    public Receiving getCareerStats(Long playerId) {

        try {
            return jdbcClient.sql("SELECT playerId, " +
                            "playerName, " +
                            "SUM(yards) as yards, " +
                            "SUM(receptions) as receptions, " +
                            "SUM(touchdowns) as touchdowns, " +
                            "MAX(longest) as longest " +
                            "FROM sport_tracker.receivingstats " +
                            "WHERE playerId = :playerId " +
                            "GROUP BY playerId")
                    .param("playerId", playerId)
                    .query(Receiving.class)
                    .single();
        } catch (Exception e) {
            Receiving emptyStats = new Receiving();
            emptyStats.setPlayerId(playerId);
            emptyStats.setPlayerName("Unknown");
            emptyStats.setYards(0);
            emptyStats.setReceptions(0);
            emptyStats.setTouchdowns(0);
            emptyStats.setLongest(0);
            return emptyStats;
        }
    }
}

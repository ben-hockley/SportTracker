package com.SportTracker.game;

import com.SportTracker.team.Team;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

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

        Long seasonId = game.getSeasonId();

        // Optional fields
        Integer homeTeamScoreQ1 = game.getHomeTeamScoreQ1();
        Integer homeTeamScoreQ2 = game.getHomeTeamScoreQ2();
        Integer homeTeamScoreQ3 = game.getHomeTeamScoreQ3();
        Integer homeTeamScoreQ4 = game.getHomeTeamScoreQ4();
        Integer awayTeamScoreQ1 = game.getAwayTeamScoreQ1();
        Integer awayTeamScoreQ2 = game.getAwayTeamScoreQ2();
        Integer awayTeamScoreQ3 = game.getAwayTeamScoreQ3();
        Integer awayTeamScoreQ4 = game.getAwayTeamScoreQ4();

        String homePassing = game.getHomePassing();
        String awayPassing = game.getAwayPassing();
        String homeRushing = game.getHomeRushing();
        String awayRushing = game.getAwayRushing();
        String homeReceiving = game.getHomeReceiving();
        String awayReceiving = game.getAwayReceiving();

        jdbcClient.sql("INSERT INTO sport_tracker.game (date,homeTeamId, awayTeamId, homeTeamScore, awayTeamScore, seasonId, homeTeamScoreQ1, homeTeamScoreQ2, homeTeamScoreQ3, homeTeamScoreQ4, awayTeamScoreQ1, awayTeamScoreQ2, awayTeamScoreQ3, awayTeamScoreQ4, homePassing, awayPassing, homeRushing, awayRushing, homeReceiving, awayReceiving) VALUES (:date, :homeTeamId, :awayTeamId, :homeTeamScore, :awayTeamScore, :seasonId, :homeTeamScoreQ1, :homeTeamScoreQ2, :homeTeamScoreQ3, :homeTeamScoreQ4, :awayTeamScoreQ1, :awayTeamScoreQ2, :awayTeamScoreQ3, :awayTeamScoreQ4, :homePassing, :awayPassing, :homeRushing, :awayRushing, :homeReceiving, :awayReceiving)")
                .param("date", gameDate)
                .param("homeTeamId", homeTeamId)
                .param("awayTeamId", awayTeamId)
                .param("homeTeamScore", homeTeamScore)
                .param("awayTeamScore", awayTeamScore)
                .param("seasonId", seasonId)
                .param("homeTeamScoreQ1", homeTeamScoreQ1)
                .param("homeTeamScoreQ2", homeTeamScoreQ2)
                .param("homeTeamScoreQ3", homeTeamScoreQ3)
                .param("homeTeamScoreQ4", homeTeamScoreQ4)
                .param("awayTeamScoreQ1", awayTeamScoreQ1)
                .param("awayTeamScoreQ2", awayTeamScoreQ2)
                .param("awayTeamScoreQ3", awayTeamScoreQ3)
                .param("awayTeamScoreQ4", awayTeamScoreQ4)
                .param("homePassing", homePassing)
                .param("awayPassing", awayPassing)
                .param("homeRushing", homeRushing)
                .param("awayRushing", awayRushing)
                .param("homeReceiving", homeReceiving)
                .param("awayReceiving", awayReceiving)

                .update();


        Integer gameId = jdbcClient.sql("SELECT id FROM sport_tracker.game WHERE date = :date AND homeTeamId = :homeTeamId AND awayTeamId = :awayTeamId")
                .param("date", gameDate)
                .param("homeTeamId", homeTeamId)
                .param("awayTeamId", awayTeamId)
                .query(Integer.class)
                .single();


        // Passing stats
        String[] homePassers = homePassing.split(";");
        String[] awayPassers = awayPassing.split(";");
        for (String passer : homePassers) {

            if (passer.isEmpty()) {
                continue;
            }
            String[] stats = passer.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer completions = parseInt(stats[3].split("-")[0]);
            Integer attempts = parseInt(stats[3].split("-")[1]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer interceptions = parseInt(stats[6]);


            jdbcClient.sql("INSERT INTO sport_tracker.passingstats (gameId, playerId, homeOrAway, playerNumber, playerName, completions, attempts, yards, touchdowns, interceptions) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :completions, :attempts, :yards, :touchdowns, :interceptions)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "home")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("completions", completions)
                    .param("attempts", attempts)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("interceptions", interceptions)
                    .update();
        }
        for (String passer : awayPassers) {

            if (passer.isEmpty()) {
                continue;
            }
            String[] stats = passer.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer completions = parseInt(stats[3].split("-")[0]);
            Integer attempts = parseInt(stats[3].split("-")[1]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer interceptions = parseInt(stats[6]);


            jdbcClient.sql("INSERT INTO sport_tracker.passingstats (gameId, playerId, homeOrAway, playerNumber, playerName, completions, attempts, yards, touchdowns, interceptions) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :completions, :attempts, :yards, :touchdowns, :interceptions)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "away")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("completions", completions)
                    .param("attempts", attempts)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("interceptions", interceptions)
                    .update();
        }

        // Rushing Stats

        String[] homeRushers = homeRushing.split(";");
        String[] awayRushers = awayRushing.split(";");

        for (String rusher : homeRushers) {

            if (rusher.isEmpty()) {
                continue;
            }
            String[] stats = rusher.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer attempts = parseInt(stats[3]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer longest = parseInt(stats[6]);

            jdbcClient.sql("INSERT INTO sport_tracker.rushingstats (gameId, playerId, homeOrAway, playerNumber, playerName, attempts, yards, touchdowns, longest) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :attempts, :yards, :touchdowns, :longest)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "home")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("attempts", attempts)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("longest", longest)
                    .update();
        }

        for (String rusher : awayRushers) {

            if (rusher.isEmpty()) {
                continue;
            }
            String[] stats = rusher.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer attempts = parseInt(stats[3]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer longest = parseInt(stats[6]);

            jdbcClient.sql("INSERT INTO sport_tracker.rushingstats (gameId, playerId, homeOrAway, playerNumber, playerName, attempts, yards, touchdowns, longest) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :attempts, :yards, :touchdowns, :longest)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "away")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("attempts", attempts)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("longest", longest)
                    .update();
        }

        // Receiving Stats
        String[] homeReceivers = homeReceiving.split(";");
        String[] awayReceivers = awayReceiving.split(";");

        for (String receiver : homeReceivers) {

            if (receiver.isEmpty()) {
                continue;
            }
            String[] stats = receiver.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer receptions = parseInt(stats[3]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer longest = parseInt(stats[6]);

            jdbcClient.sql("INSERT INTO sport_tracker.receivingstats (gameId, playerId, homeOrAway, playerNumber, playerName, receptions, yards, touchdowns, longest) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :receptions, :yards, :touchdowns, :longest)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "home")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("receptions", receptions)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("longest", longest)
                    .update();
        }
        for (String receiver : awayReceivers) {

            if (receiver.isEmpty()) {
                continue;
            }
            String[] stats = receiver.split(",");

            Long playerId = parseLong(stats[0]);
            Integer playerNumber = parseInt(stats[1]);
            String playerName = stats[2];
            Integer receptions = parseInt(stats[3]);
            Integer yds = parseInt(stats[4]);
            Integer td = parseInt(stats[5]);
            Integer longest = parseInt(stats[6]);

            jdbcClient.sql("INSERT INTO sport_tracker.receivingstats (gameId, playerId, homeOrAway, playerNumber, playerName, receptions, yards, touchdowns, longest) VALUES (:gameId, :playerId, :homeOrAway, :playerNumber, :playerName, :receptions, :yards, :touchdowns, :longest)")
                    .param("gameId", gameId)
                    .param("homeOrAway", "away")
                    .param("playerId", playerId)
                    .param("playerNumber", playerNumber)
                    .param("playerName", playerName)
                    .param("receptions", receptions)
                    .param("yards", yds)
                    .param("touchdowns", td)
                    .param("longest", longest)
                    .update();
        }
    }

    public List<Game> findGamesBySeasonId(Long seasonId) {
        return jdbcClient.sql("SELECT * FROM sport_tracker.game WHERE seasonId = :seasonId")
                .param("seasonId", seasonId)
                .query(Game.class)
                .list();
    }

    public List<GameWithTeams> findGameWithTeamsBySeasonId(Long seasonId) {
        List<Game> games = findGamesBySeasonId(seasonId);

        List<GameWithTeams> gamesWithTeams = new ArrayList<>();
        for (Game game : games) {
            Team homeTeam = jdbcClient.sql("SELECT * FROM sport_tracker.team WHERE id = :id")
                    .param("id", game.getHomeTeamId())
                    .query(Team.class)
                    .single();
            Team awayTeam = jdbcClient.sql("SELECT * FROM sport_tracker.team WHERE id = :id")
                    .param("id", game.getAwayTeamId())
                    .query(Team.class)
                    .single();
            gamesWithTeams.add(new GameWithTeams(game, homeTeam, awayTeam));
        }
        return gamesWithTeams;
    }
}

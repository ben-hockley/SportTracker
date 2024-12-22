package com.SportTracker.league;

import com.SportTracker.game.Game;
import com.SportTracker.game.GameRepository;
import com.SportTracker.game.GameWithTeams;
import com.SportTracker.player.Player;
import com.SportTracker.player.PlayerStats;
import com.SportTracker.season.Season;
import com.SportTracker.season.SeasonRepository;
import com.SportTracker.team.Team;
import com.SportTracker.team.TeamRepository;
import com.SportTracker.team.TeamSeasonRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LeagueController {

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public LeagueController(LeagueRepository leagueRepository, SeasonRepository seasonRepository, GameRepository gameRepository, TeamRepository teamRepository) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping("/league/{id}")
    public String viewLeague(Model model, @PathVariable Long id) {
        League league = leagueRepository.findById(id);
        model.addAttribute("league", league);
        List<Season> seasons = seasonRepository.findByLeagueId(id);
        model.addAttribute("seasons", seasons);

        List<Team> teams = teamRepository.findByLeagueId(id);

        for (Season season : seasons) {

            List<GameWithTeams> games = gameRepository.findGameWithTeamsBySeasonId(season.getId());
            //sort games by date, most recent first
            games.sort((a, b) -> b.getDate().compareTo(a.getDate()));
            season.setGames(games);

            List<TeamSeasonRecord> standings = new ArrayList<>();
            for (Team team : teams) {
                // create a record for each team in the league
                standings.add(new TeamSeasonRecord(team, 0, 0, 0, 0, 0));
            }

            // go through every game in the season, and update stats for the home and away team.
            for (GameWithTeams game : season.getGames()){
                for (TeamSeasonRecord record : standings) {
                    // if a record contains one of the teams, then update that record.
                        if (record.getTeam().getId() == game.getHomeTeam().getId()) {

                            System.out.println("Home team: " + game.getHomeTeam().getName());

                            record.setPointsFor(record.getPointsFor() + game.getHomeTeamScore());
                            record.setPointsAgainst(record.getPointsAgainst() + game.getAwayTeamScore());

                            if (game.getHomeTeamScore() > game.getAwayTeamScore()) {
                                record.setWins(record.getWins() + 1);
                            } else if (game.getHomeTeamScore() == game.getAwayTeamScore()){
                                record.setTies(record.getTies() + 1);
                            } else {
                                record.setLosses(record.getLosses() + 1);
                            }
                        } else if (record.getTeam().getId() == game.getAwayTeam().getId()) {

                            System.out.println("Away team: " + game.getAwayTeam().getName());

                            record.setPointsFor(record.getPointsFor() + game.getAwayTeamScore());
                            record.setPointsAgainst(record.getPointsAgainst() + game.getHomeTeamScore());

                            if (game.getAwayTeamScore() > game.getHomeTeamScore()) {
                                record.setWins(record.getWins() + 1);
                            } else if (game.getHomeTeamScore() == game.getAwayTeamScore()){
                                record.setTies(record.getTies() + 1);
                            } else {
                                record.setLosses(record.getLosses() + 1);
                            }
                        }
                    }
                }

            //sort standings, by wins first, then ties, then points scored, then least points against.
            standings.sort((a, b) -> {
                if (a.getWins() != b.getWins()) {
                    return b.getWins() - a.getWins();
                } else if (a.getTies() != b.getTies()) {
                    return b.getTies() - a.getTies();
                } else if (a.getPointsFor() != b.getPointsFor()) {
                    return b.getPointsFor() - a.getPointsFor();
                } else {
                    return a.getPointsAgainst() - b.getPointsAgainst();
                }
            });

            season.setStandings(standings);
            }
        model.addAttribute("seasons", seasons);

        // GET LEADING PLAYERS IN PASSING YARDS FOR EACH SEASON OF THE LEAGUE
        List<List<PlayerStats>> allSeasonsPassingYards = new ArrayList<>();

        // for each season in the league
        for (Season season : seasons) {
            // get all games in the season
            List<Game> games = gameRepository.findGamesBySeasonId(season.getId());
            List<String> passingRawStrings = new ArrayList<>();
            // for each game in the season
            for (Game game : games) {
                // get the passing stats for the home and away team
                passingRawStrings.add(game.getHomePassing());
                passingRawStrings.add(game.getAwayPassing());
            }
            // remove any empty strings from the list
            passingRawStrings.removeIf(String::isEmpty);
            List<String> passingPlayerStats = new ArrayList<>();
            // for each raw string, split it into individual player stats
            for (String rawString : passingRawStrings) {
                String[] playerStats = rawString.split(";");
                for (String playerStat : playerStats) {
                    if (!playerStat.isEmpty()) {
                        passingPlayerStats.add(playerStat);
                    }
                }
            }
            // sort list of player stats by player id
            passingPlayerStats.sort((a, b) -> {
                String[] aSplit = a.split(",");
                String[] bSplit = b.split(",");
                return Integer.parseInt(aSplit[0]) - Integer.parseInt(bSplit[0]);
            });

            List<PlayerStats> passingYards = new ArrayList<>();

            for (String playerPassingStats : passingPlayerStats) {
                String[] stats = playerPassingStats.split(",");
                if (passingYards.isEmpty() || passingYards.getLast().getPlayerId() != Integer.parseInt(stats[0])) {
                    passingYards.add(new PlayerStats(season.getId(),Long.parseLong(stats[0]), stats[2], Integer.parseInt(stats[4])));
                } else {
                    passingYards.getLast().setPlayerStat(passingYards.getLast().getPlayerStat() + Integer.parseInt(stats[4]));
                }
            }

            System.out.println("Passing yards for season " + season.getYear());
            for (PlayerStats playerStats : passingYards) {
                System.out.println(playerStats.getPlayerName() + " " + playerStats.getPlayerStat());
            }

            passingYards.sort((a, b) -> b.getPlayerStat() - a.getPlayerStat());
            allSeasonsPassingYards.add(passingYards);

        }

        model.addAttribute("passingYardsLeadersBySeason", allSeasonsPassingYards);

        return "leagueDetails";
    }
}

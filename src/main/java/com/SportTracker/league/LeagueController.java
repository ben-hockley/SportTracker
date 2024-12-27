package com.SportTracker.league;

import com.SportTracker.game.GameRepository;
import com.SportTracker.game.GameWithTeams;
import com.SportTracker.season.Season;
import com.SportTracker.season.SeasonRepository;
import com.SportTracker.stats.*;
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
    private final PassingRepository passingRepository;
    private final RushingRepository rushingRepository;
    private final ReceivingRepository receivingRepository;

    public LeagueController(LeagueRepository leagueRepository, SeasonRepository seasonRepository, GameRepository gameRepository, TeamRepository teamRepository, PassingRepository passingRepository, RushingRepository rushingRepository, ReceivingRepository receivingRepository) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.passingRepository = passingRepository;
        this.rushingRepository = rushingRepository;
        this.receivingRepository = receivingRepository;
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

        // GET Stat leaders for different stat categories for the season (passing, rushing, receiving)
        List<List<Passing>> passingLeadersBySeason = new ArrayList<>();
        List<List<Rushing>> rushingLeadersBySeason = new ArrayList<>();
        List<List<Receiving>> receivingLeadersBySeason = new ArrayList<>();

        for (Season season : seasons) {
            List<Passing> passingLeaders = passingRepository.getPassingLeadersBySeason(season.getId());
            List<Rushing> rushingLeaders = rushingRepository.getRushingLeadersBySeason(season.getId());
            List<Receiving> receivingLeaders = receivingRepository.getReceivingLeadersBySeason(season.getId());

            passingLeadersBySeason.add(passingLeaders);
            rushingLeadersBySeason.add(rushingLeaders);
            receivingLeadersBySeason.add(receivingLeaders);
        }

        model.addAttribute("passingLeadersBySeason", passingLeadersBySeason);
        model.addAttribute("rushingLeadersBySeason", rushingLeadersBySeason);
        model.addAttribute("receivingLeadersBySeason", receivingLeadersBySeason);

        return "/league/leagueDetails";
    }
}

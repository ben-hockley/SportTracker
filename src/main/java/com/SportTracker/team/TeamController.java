package com.SportTracker.team;

import com.SportTracker.game.Game;
import com.SportTracker.game.GameRepository;
import com.SportTracker.game.GameWithTeams;
import com.SportTracker.league.League;
import com.SportTracker.league.LeagueRepository;
import com.SportTracker.player.Player;
import com.SportTracker.player.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;

    public TeamController(TeamRepository teamRepository, PlayerRepository playerRepository, GameRepository gameRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
    }

    @GetMapping("/allTeams")
    public String allTeams(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("leagues", leagueRepository.findAll());
        return "/team/allTeams";
    }

    @GetMapping("/newTeam")
    public String newTeam(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("leagues", leagueRepository.findAll());
        return "/team/newTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(Team team) {
        teamRepository.save(team);
        return "redirect:/allTeams";
    }

    @GetMapping("/teamDetails/{id}")
    public String teamDetails(Model model, @PathVariable Long id) {

        // Get the team by id
        Team team = teamRepository.findById(id);

        League league = leagueRepository.findById(team.getLeagueId());

        // Get the list of players for the team
        List<Player> players = playerRepository.findByTeamId(id);

        List<Game> gamesInDatabase = gameRepository.findByTeamId(id);

        List<GameWithTeams> allGamesWithTeams = new ArrayList<>();
        for (Game game : gamesInDatabase) {
            Team homeTeam = teamRepository.findById(game.getHomeTeamId().longValue());
            Team awayTeam = teamRepository.findById(game.getAwayTeamId().longValue());
            allGamesWithTeams.add(new GameWithTeams(game, homeTeam, awayTeam));
        }

        model.addAttribute("games", allGamesWithTeams);

        model.addAttribute("league", league);

        model.addAttribute("team", team);
        model.addAttribute("players", players);
        return "/team/teamDetails";
    }
}
package com.SportTracker.game;

import com.SportTracker.player.PlayerRepository;
import com.SportTracker.team.Team;
import com.SportTracker.team.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public GameController(GameRepository gameRepository, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/allGames")
    public String allGames(Model model) {

        List<Game> allGamesInDatabase = gameRepository.findAll();
        List<GameWithTeams> allGamesWithTeams = new ArrayList<>();
        for (Game game : allGamesInDatabase) {
            Team homeTeam = teamRepository.findById(game.getHomeTeamId().longValue());
            Team awayTeam = teamRepository.findById(game.getAwayTeamId().longValue());
            allGamesWithTeams.add(new GameWithTeams(game, homeTeam, awayTeam));
        }

        model.addAttribute("games", allGamesWithTeams);

        return "allGames";
    }

    @GetMapping("/newGame")
    public String newGame(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("players", playerRepository.findAll());
        model.addAttribute("game", new Game());
        return "newGame";
    }

    @PostMapping("/addGame")
    public String addGame(Model model, Game game) {
        gameRepository.save(game);
        return "redirect:/allGames";
    }

    @GetMapping("/gameDetails/{id}")
    public String gameDetails(Model model, @PathVariable Long id) {
        Game game = gameRepository.findById(id);
        Team homeTeam = teamRepository.findById(game.getHomeTeamId().longValue());
        Team awayTeam = teamRepository.findById(game.getAwayTeamId().longValue());
        model.addAttribute("game", game);
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);
        return "gameDetails";
    }
}

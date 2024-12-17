package com.SportTracker.game;

import com.SportTracker.team.Team;
import com.SportTracker.team.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    public GameController(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/allGames")
    public String allGames(Model model) {

        List<Game> allGamesInDatabase = gameRepository.findAll();
        List<GameWithTeams> allGamesWithTeams = new ArrayList<GameWithTeams>();
        for (Game game : allGamesInDatabase) {
            Team homeTeam = teamRepository.findByName(game.getHomeTeamName());
            Team awayTeam = teamRepository.findByName(game.getAwayTeamName());
            allGamesWithTeams.add(new GameWithTeams(game, homeTeam, awayTeam));
        }

        model.addAttribute("games", allGamesWithTeams);


        //model.addAttribute("games", gameRepository.findAll());
        return "allGames";
    }

    @GetMapping("/newGame")
    public String newGame(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("game", new Game());
        return "newGame";
    }

    @PostMapping("/addGame")
    public String addGame(Model model, Game game) {
        gameRepository.save(game);
        return "redirect:/allGames";
    }
}

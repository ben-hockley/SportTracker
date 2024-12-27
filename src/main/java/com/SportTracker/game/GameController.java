package com.SportTracker.game;

import com.SportTracker.league.League;
import com.SportTracker.league.LeagueRepository;
import com.SportTracker.player.PlayerRepository;
import com.SportTracker.season.Season;
import com.SportTracker.season.SeasonRepository;
import com.SportTracker.stats.*;
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
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final PassingRepository passingRepository;
    private final RushingRepository rushingRepository;
    private final ReceivingRepository receivingRepository;

    public GameController(GameRepository gameRepository, TeamRepository teamRepository, PlayerRepository playerRepository, SeasonRepository seasonRepository, LeagueRepository leagueRepository, PassingRepository passingRepository, RushingRepository rushingRepository, ReceivingRepository receivingRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
        this.passingRepository = passingRepository;
        this.rushingRepository = rushingRepository;
        this.receivingRepository = receivingRepository;
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

        return "/game/allGames";
    }

    @GetMapping("/newGame")
    public String newGame(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("players", playerRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());
        model.addAttribute("leagues", leagueRepository.findAll());
        model.addAttribute("game", new Game());
        return "/game/newGame";
    }

    @PostMapping("/addGame")
    public String addGame(Game game) {
        gameRepository.save(game);
        return "redirect:/allGames";
    }

    @GetMapping("/gameDetails/{id}")
    public String gameDetails(Model model, @PathVariable Long id) {
        Game game = gameRepository.findById(id);
        Team homeTeam = teamRepository.findById(game.getHomeTeamId().longValue());
        Team awayTeam = teamRepository.findById(game.getAwayTeamId().longValue());
        Season season = seasonRepository.findById(game.getSeasonId());
        League league = leagueRepository.findById(season.getLeagueId());
        List<Passing> homePassing = passingRepository.findByGameIdAndHomeOrAway(id, "home");
        List<Passing> awayPassing = passingRepository.findByGameIdAndHomeOrAway(id, "away");
        List<Rushing> homeRushing = rushingRepository.findByGameIdAndHomeOrAway(id, "home");
        List<Rushing> awayRushing = rushingRepository.findByGameIdAndHomeOrAway(id, "away");
        List<Receiving> homeReceiving = receivingRepository.findByGameIdAndHomeOrAway(id, "home");
        List<Receiving> awayReceiving = receivingRepository.findByGameIdAndHomeOrAway(id, "away");

        model.addAttribute("game", game);
        model.addAttribute("season", season);
        model.addAttribute("league", league);
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);
        model.addAttribute("players", playerRepository.findAll());

        model.addAttribute("homePassing", homePassing);
        model.addAttribute("awayPassing", awayPassing);
        model.addAttribute("homeRushing", homeRushing);
        model.addAttribute("awayRushing", awayRushing);
        model.addAttribute("homeReceiving", homeReceiving);
        model.addAttribute("awayReceiving", awayReceiving);
        return "/game/gameDetails";
    }
}

package com.SportTracker.player;

import com.SportTracker.game.Game;
import com.SportTracker.game.GameRepository;
import com.SportTracker.game.GameWithTeams;
import com.SportTracker.league.League;
import com.SportTracker.league.LeagueRepository;
import com.SportTracker.stats.PassingRepository;
import com.SportTracker.stats.ReceivingRepository;
import com.SportTracker.stats.RushingRepository;
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
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final GameRepository gameRepository;
    private final PassingRepository passingRepository;
    private final RushingRepository rushingRepository;
    private final ReceivingRepository receivingRepository;

    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository, LeagueRepository leagueRepository, GameRepository gameRepository, PassingRepository passingRepository, RushingRepository rushingRepository, ReceivingRepository receivingRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.gameRepository = gameRepository;
        this.passingRepository = passingRepository;
        this.rushingRepository = rushingRepository;
        this.receivingRepository = receivingRepository;
    }

    @GetMapping("/allPlayers")
    public String allPlayers(Model model) {
        List<Player> allPlayersInDatabase = playerRepository.findAll();
        List<PlayerWithTeam> allPlayersWithTeam = new ArrayList<>();
        for (Player player : allPlayersInDatabase) {
            Team team = teamRepository.findById(player.getTeamId());
            allPlayersWithTeam.add(new PlayerWithTeam(player, team));
        }

        model.addAttribute("players", allPlayersWithTeam);
        model.addAttribute("teams", teamRepository.findAll());

        return "/player/allPlayers";
    }

    @GetMapping("/newPlayer")
    public String newPlayer(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("player", new Player());
        return "/player/newPlayer";
    }

    @PostMapping("/addPlayer")
    public String addPlayer(Player player) {
        playerRepository.save(player);
        return "redirect:/allPlayers";
    }

    @GetMapping("/playerDetails/{id}")
    public String playerDetails(Model model, @PathVariable Long id) {
        // Get the player by id
        Player player = playerRepository.findById(id);

        if (player == null) {
            String ERROR_MESSAGE = "Player not found";
            model.addAttribute("errorMessage", ERROR_MESSAGE);
            return "error";
        }

        // Get the team for the player
        Team team = teamRepository.findById(player.getTeamId());
        League league = leagueRepository.findById(team.getLeagueId());

        List<GameWithTeams> games = gameRepository.findGameWithTeamsByTeamId(team.getId());
        // sort the games by date, most recent games first.
        games.sort((g1, g2) -> g2.getDate().compareTo(g1.getDate()));

        System.out.println("Player position: " + player.getPosition());

        if (player.getPosition().equals("QB")) {
            model.addAttribute("stats", passingRepository.findByPlayerId(player.getId()));
            model.addAttribute("careerStats", passingRepository.getCareerStats(player.getId()));
        } else if (player.getPosition().equals("RB")) {
            model.addAttribute("stats", rushingRepository.findByPlayerId(player.getId()));
            model.addAttribute("careerStats", rushingRepository.getCareerStats(player.getId()));
        } else if (player.getPosition().equals("WR") || player.getPosition().equals("TE")) {
            model.addAttribute("stats", receivingRepository.findByPlayerId(player.getId()));
            model.addAttribute("careerStats", receivingRepository.getCareerStats(player.getId()));
        }

        model.addAttribute("games", games);
        model.addAttribute("player", player);
        model.addAttribute("team", team);
        model.addAttribute("league", league);
        return "/player/playerDetails";
    }

    @GetMapping("/editPlayer/{id}")
    public String editPlayer(Model model, @PathVariable Long id) {
        Player player = playerRepository.findById(id);
        model.addAttribute("player", player);
        model.addAttribute("teams", teamRepository.findAll());
        return "/player/editPlayer";
    }

    @PostMapping("/updatePlayer/{id}")
    public String updatePlayer(@PathVariable Long id, Player player) {
        playerRepository.update(player);
        return "redirect:/playerDetails/" + id;
    }

    @GetMapping("/deletePlayer/{id}")
    public String deletePlayer(@PathVariable Long id) {
        playerRepository.deleteById(id);
        return "redirect:/allPlayers";
    }

}

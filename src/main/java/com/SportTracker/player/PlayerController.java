package com.SportTracker.player;

import com.SportTracker.league.League;
import com.SportTracker.league.LeagueRepository;
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

    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
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
    public String addPlayer(Model model, Player player) {
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
    public String updatePlayer(Model model, @PathVariable Long id, Player player) {
        playerRepository.update(player);
        return "redirect:/playerDetails/" + id;
    }

    @GetMapping("/deletePlayer/{id}")
    public String deletePlayer(Model model, @PathVariable Long id) {
        playerRepository.deleteById(id);
        return "redirect:/allPlayers";
    }

}

package com.SportTracker.player;

import com.SportTracker.game.Game;
import com.SportTracker.game.GameWithTeams;
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

    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/allPlayers")
    public String allPlayers(Model model) {
        List<Player> allPlayersInDatabase = playerRepository.findAll();
        List<PlayerWithTeam> allPlayersWithTeam = new ArrayList<PlayerWithTeam>();
        for (Player player : allPlayersInDatabase) {
            Team team = teamRepository.findById(player.getTeamId());
            allPlayersWithTeam.add(new PlayerWithTeam(player, team));
        }

        model.addAttribute("players", allPlayersWithTeam);

        return "allPlayers";
    }

    @GetMapping("/newPlayer")
    public String newPlayer(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("player", new Player());
        return "newPlayer";
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

        // Get the team for the player
        Team team = teamRepository.findById(player.getTeamId());
        model.addAttribute("player", player);
        model.addAttribute("team", team);
        return "playerDetails";
    }

}

package com.SportTracker.team;

import com.SportTracker.player.Player;
import com.SportTracker.player.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TeamController {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamController(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/allTeams")
    public String allTeams(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "allTeams";
    }

    @GetMapping("/newTeam")
    public String newTeam(Model model) {
        model.addAttribute("team", new Team());
        return "newTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(Model model,Team team) {
        teamRepository.save(team);
        return "redirect:/allTeams";
    }

    @GetMapping("/teamDetails/{id}")
    public String teamDetails(Model model, @PathVariable Long id) {

        // Get the team by id
        Team team = teamRepository.findById(id);

        // Get the list of players for the team
        List<Player> players = playerRepository.findByTeamId(id);
        model.addAttribute("team", team);
        model.addAttribute("players", players);
        return "teamDetails";
    }

}

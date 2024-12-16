package com.SportTracker.team;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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

}

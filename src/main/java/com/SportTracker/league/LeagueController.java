package com.SportTracker.league;

import com.SportTracker.game.GameRepository;
import com.SportTracker.season.Season;
import com.SportTracker.season.SeasonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LeagueController {

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final GameRepository gameRepository;

    public LeagueController(LeagueRepository leagueRepository, SeasonRepository seasonRepository, GameRepository gameRepository) {
        this.leagueRepository = leagueRepository;
        this.seasonRepository = seasonRepository;
        this.gameRepository = gameRepository;
    }
    @GetMapping("/league/{id}")
    public String viewLeague(Model model, @PathVariable Long id) {
        League league = leagueRepository.findById(id);
        model.addAttribute("league", league);
        List<Season> seasons = seasonRepository.findByLeagueId(id);
        model.addAttribute("seasons", seasons);

        for (Season season : seasons) {
            season.setGames(gameRepository.findGameWithTeamsBySeasonId(season.getId()));
        }
        model.addAttribute("seasons", seasons);
        return "leagueDetails";
    }
}

package com.SportTracker.season;

import com.SportTracker.game.GameWithTeams;
import com.SportTracker.team.TeamSeasonRecord;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Season {
    @Id
    private Long id;

    private Integer year;
    private Long leagueId;
    private List<GameWithTeams> games;
    private List<TeamSeasonRecord> standings;
}

package com.SportTracker.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamSeasonRecord {
    Team team;
    Integer wins;
    Integer losses;
    Integer ties;
    Integer pointsFor;
    Integer pointsAgainst;
}

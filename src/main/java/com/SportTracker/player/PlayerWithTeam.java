package com.SportTracker.player;

import com.SportTracker.team.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerWithTeam {
    private Long id;
    private String name;
    private Team team;
    private String position; // Positions referred tp by their abbreviations (e.g. QB, RB, WR, TE, OL, DL, LB, CB, S, K, P, ATH)
    private Integer number;

    private Integer recruitYear;
    private Integer recruitStars;
    private Integer draftYear;
    private Integer draftPick;

    public PlayerWithTeam(Player player, Team team) {
        this.id = player.getId();
        this.name = player.getName();
        this.team = team;
        this.position = player.getPosition();
        this.number = player.getNumber();

        this.recruitYear = player.getRecruitYear();
        this.recruitStars = player.getRecruitStars();
        this.draftYear = player.getDraftYear();
        this.draftPick = player.getDraftPick();
    }
}

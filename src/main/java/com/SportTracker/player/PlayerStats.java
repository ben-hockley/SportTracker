package com.SportTracker.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {
    private long seasonId;
    private long playerId;
    private String playerName;
    private int playerStat;
}

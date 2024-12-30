package com.SportTracker.stats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Receiving {
    private long id;
    private long seasonId;
    private long gameId;
    private String homeOrAway;

    private long playerId;
    private int playerNumber;
    private String playerName;

    private int receptions;
    private int yards;
    private int touchdowns;
    private int longest;
}

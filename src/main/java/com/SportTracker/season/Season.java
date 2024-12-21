package com.SportTracker.season;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Season {
    @Id
    private Long id;

    private Integer year;
    private Long leagueId;
}

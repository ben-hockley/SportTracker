package com.SportTracker.team;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Team {
    @Id
    private Long id;
    private String name;
    private String league;
    private String logoUrl;
}

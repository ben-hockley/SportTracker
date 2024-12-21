package com.SportTracker.league;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class League {
    @Id
    private long id;
    private String name;
}

package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class PersonalStatsDto {
	
    // Personal Data
    private Map<Date, Long> matchesPlayedPerDay = new HashMap<>();
    private Map<Date, Long> secondsPlayedPerDay = new HashMap<>();
    private Map<Date, Long> eloPerDay = new HashMap<>();

}

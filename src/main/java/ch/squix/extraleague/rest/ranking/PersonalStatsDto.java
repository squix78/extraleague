package ch.squix.extraleague.rest.ranking;

import lombok.Data;

@Data
public class PersonalStatsDto {
	
    // Personal Data
    private Long matchesPlayedYesterday;
    private Long matchesPlayedToday;
    private Long secondsPlayedYesterday;
    private Long secondsPlayedToday;

}

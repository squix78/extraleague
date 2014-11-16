package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PersonalStatsDtoMapper {
	
	public static PersonalStatsDto mapToDto(PlayerRanking ranking) {
		PersonalStatsDto dto = new PersonalStatsDto();
		dto.setMatchesPlayedPerDay(ranking.getMatchesPlayedPerDay());
		dto.setSecondsPlayedPerDay(ranking.getSecondsPlayedPerDay());
		dto.setEloPerDay(ranking.getEloPerDay());
		return dto;
	}

}

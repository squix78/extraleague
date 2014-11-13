package ch.squix.extraleague.rest.ranking;

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

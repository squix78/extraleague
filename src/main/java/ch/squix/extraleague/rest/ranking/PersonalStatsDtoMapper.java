package ch.squix.extraleague.rest.ranking;

import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PersonalStatsDtoMapper {
	
	public static PersonalStatsDto mapToDto(PlayerRanking ranking) {
		PersonalStatsDto dto = new PersonalStatsDto();
		dto.setMatchesPlayedToday(ranking.getMatchesPlayedToday());
		dto.setMatchesPlayedYesterday(ranking.getMatchesPlayedYesterday());
		dto.setSecondsPlayedToday(ranking.getSecondsPlayedToday());
		dto.setSecondsPlayedYesterday(ranking.getSecondsPlayedYesterday());
		return dto;
	}

}

package ch.squix.extraleague.rest.matches;

import ch.squix.extraleague.model.match.Match;

public class MatchDtoMapper {

	public static MatchDto mapToDto(Match match) {
		MatchDto dto = new MatchDto();
		dto.setId(match.getId());
		dto.setGameId(match.getGameId());
		dto.setTeamA(match.getTeamA());
		dto.setTeamB(match.getTeamB());
		dto.setTeamAScore(match.getTeamAScore());
		dto.setTeamBScore(match.getTeamBScore());
		dto.setStartDate(match.getStartDate());
		dto.setEndDate(match.getEndDate());
		dto.setMatchIndex(match.getMatchIndex());
		return dto;
	}


}

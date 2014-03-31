package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.List;

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
		dto.setWinProbabilityTeamA(match.getWinProbabilityTeamA());
		return dto;
	}
	
	public static List<MatchDto> mapToDtoList(List<Match> matches) {
		List<MatchDto> dtos = new ArrayList<>();
		for (Match match : matches) {
			dtos.add(mapToDto(match));
		}
		return dtos;
	}


}

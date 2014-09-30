package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

import ch.squix.extraleague.model.match.Goal;
import ch.squix.extraleague.model.match.Match;

public class MatchDtoMapper {

	public static MatchDto mapToDto(Match match) {
		MatchDto dto = new MatchDto();
		String websafeKey = Key.create(match.getGameKey(), Match.class, match.getId()).getString();
		dto.setKey(websafeKey);
		dto.setGameId(match.getGameId());
		dto.setTeamA(match.getTeamA());
		dto.setTeamB(match.getTeamB());
		dto.setTeamAScore(match.getTeamAScore());
		dto.setTeamBScore(match.getTeamBScore());
		dto.setScorers(match.getScorers());
		dto.setStartDate(match.getStartDate());
		dto.setEndDate(match.getEndDate());
		dto.setMatchIndex(match.getMatchIndex());
		dto.setWinProbabilityTeamA(match.getWinProbabilityTeamA());
		dto.setWinPointsTeamA(match.getWinPointsTeamA());
		dto.setWinPointsTeamB(match.getWinPointsTeamB());
		dto.setMaxGoals(match.getMaxGoals());
		dto.setMaxMatches(match.getMaxMatches());
		dto.setPositionSwappingAllowed(match.getPositionSwappingAllowed());
		dto.setMatchInfo(MatchInfoService.getMatchInfoDto(match));
		for (Goal goal : match.getGoals()) {
			GoalDto goalDto = new GoalDto();
			goalDto.setScorer(goal.getScorer());
			goalDto.setTime(goal.getTime());
			dto.getGoals().add(goalDto);
		}
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

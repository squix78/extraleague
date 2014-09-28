package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Goal;
import ch.squix.extraleague.model.match.Match;

public class MatchInfoService {
	
	public static MatchInfoDto getMatchInfoDto(Match match) {
		MatchInfoDto infoDto = new MatchInfoDto();
		
		Map<String, Integer> goalMap = new HashMap<>();
		List<String> scorers = match.getScorers();
		for (String player : match.getPlayers()) {
			goalMap.put(player, Collections.frequency(scorers, player));
		}
		infoDto.setGoalMap(goalMap); 
		infoDto.setTeamAGoals(getScorers(match.getStartDate(), Arrays.asList(match.getTeamA()), match.getGoals()));
		infoDto.setTeamBGoals(getScorers(match.getStartDate(), Arrays.asList(match.getTeamB()), match.getGoals()));
		
		
		return infoDto;
	}
	
	private static List<GoalDto> getScorers(Date matchStartTime, List<String> team, List<Goal> goals) {
		List<GoalDto> goalDtos = new ArrayList<>();
		for (Goal goal : goals) {
			if (team.contains(goal.getScorer())) {
				Long minutesSinceMatchStart = (goal.getTime().getTime() - matchStartTime.getTime()) / (1000 * 60);
				GoalDto dto = new GoalDto();
				dto.setScorer(goal.getScorer());
				dto.setTime(goal.getTime());
				dto.setMinutesSinceMatchStart(minutesSinceMatchStart);
				goalDtos.add(dto);
			}
		}
		return goalDtos;
	}

}

package ch.squix.extraleague.rest.matches;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		return infoDto;
	}

}

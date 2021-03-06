package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MatchInfoDto {
	
	private Map<String, Integer> goalMap = new HashMap<>();
	private Map<String, Double> shareMap = new HashMap<>();
	private List<GoalDto> teamAGoals = new ArrayList<>();
	private List<GoalDto> teamBGoals = new ArrayList<>();
	private List<EventDto> events = new ArrayList<>();

}

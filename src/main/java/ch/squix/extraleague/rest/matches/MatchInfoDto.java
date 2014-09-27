package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MatchInfoDto {
	
	private Map<String, Integer> goalMap = new HashMap<>();
	private List<String> teamAScorers = new ArrayList<>();
	private List<String> teamBScorers = new ArrayList<>();

}

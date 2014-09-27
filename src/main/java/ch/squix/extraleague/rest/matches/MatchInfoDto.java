package ch.squix.extraleague.rest.matches;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class MatchInfoDto {
	
	private Map<String, Integer> goalMap = new HashMap<>();

}

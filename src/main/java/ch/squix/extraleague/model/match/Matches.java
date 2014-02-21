package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Matches {

	private Map<String, List<Match>> matchPlayerMap = new HashMap<>();
	private List<Match> matches = new ArrayList<>();
        private Map<Long, List<Match>> gameMap = new HashMap<>();

	
	public void setMatches(List<Match> matches) {
		this.matches = matches;
		matchPlayerMap.clear();
		for (Match match : matches) {
			List<String> players = new ArrayList<>(Arrays.asList(match.getTeamA()));
			players.addAll(Arrays.asList(match.getTeamB()));
			for (String player : players) {
				List<Match> playerMatches = matchPlayerMap.get(player);
				if (playerMatches == null) {
					playerMatches = new ArrayList<>();
					matchPlayerMap.put(player, playerMatches);
				}
				playerMatches.add(match);
			}
		}
	        for (Match match : matches) {
	                List<Match> gameMatches = gameMap.get(match.getGameId());
	                if (gameMatches == null) {
	                        gameMatches = new ArrayList<>();
	                        gameMap.put(match.getGameId(), gameMatches);
	                }
	                gameMatches.add(match);
	        }
	}
	
	public Map<Long, List<Match>> getGameMatches() {
	    return gameMap;
	}
	
	public Set<String> getPlayers() {
		return matchPlayerMap.keySet();
	}
	
	public List<Match> getMatchesByPlayer(String player) {
		return matchPlayerMap.get(player);
	}
	
	public List<Match> getMatches() {
		return matches;
	}
	
}

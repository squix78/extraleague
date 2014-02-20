package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Matches {

	private Map<String, List<Match>> matchPlayerMap = new HashMap<>();
	private List<Match> matches = new ArrayList<>();
	
	public void setMatches(List<Match> matches) {
		this.matches = matches;
		matchPlayerMap.clear();
		for (Match match : matches) {
			List<String> players = Arrays.asList(match.getTeamA());
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
		MatchDateComparator comparator = new MatchDateComparator();
		for (Map.Entry<String, List<Match>> entry : matchPlayerMap.entrySet()) {
			Collections.sort(entry.getValue(), comparator);
		}
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
	
	private class MatchDateComparator implements Comparator<Match> {

		@Override
		public int compare(Match o1, Match o2) {
			return o1.getStartDate().compareTo(o2.getStartDate());
		}
		
	}
	
}

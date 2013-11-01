package ch.squix.extraleague.rest.result;

import java.util.Map;
import java.util.TreeMap;

public class SummaryDto {
	
	private Map<String, Integer> playerScore = new TreeMap<>();

	/**
	 * @return the playerScore
	 */
	public Map<String, Integer> getPlayerScore() {
		return playerScore;
	}

	/**
	 * @param playerScore the playerScore to set
	 */
	public void setPlayerScore(Map<String, Integer> playerScore) {
		this.playerScore = playerScore;
	}

}

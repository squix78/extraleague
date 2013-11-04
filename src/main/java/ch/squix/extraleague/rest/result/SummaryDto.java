package ch.squix.extraleague.rest.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SummaryDto {
	
	private List<PlayerScoreDto> playerScores = new ArrayList<>();

	/**
	 * @return the playerScores
	 */
	public List<PlayerScoreDto> getPlayerScores() {
		return playerScores;
	}

	/**
	 * @param playerScores the playerScores to set
	 */
	public void setPlayerScores(List<PlayerScoreDto> playerScores) {
		this.playerScores = playerScores;
	}

}

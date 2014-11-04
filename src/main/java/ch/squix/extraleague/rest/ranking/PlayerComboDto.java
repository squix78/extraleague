package ch.squix.extraleague.rest.ranking;

import lombok.Data;

@Data
public class PlayerComboDto {
	
	private String player;
	private String combo;
	private Integer gamesWon = 0;
	private Integer gamesLost = 0;
	private Integer goalsReceived = 0;
	private Integer goalsMade = 0;
	
	public Double getSuccessRate() {
		return 1.0 * gamesWon / (gamesWon + gamesLost);
	}
	
	public Integer getTotalGames() {
		return gamesWon + gamesLost;
	}
	
	public Double getKeeperGoalRate() {
		return 1.0 * goalsReceived / (gamesWon + gamesLost);
	}

}

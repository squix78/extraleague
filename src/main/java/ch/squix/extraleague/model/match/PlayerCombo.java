package ch.squix.extraleague.model.match;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlayerCombo implements Serializable {
	
	private static final long serialVersionUID = -1087465634185379532L;
	
	private String player;
	private String combo;
	private Integer gamesWon = 0;
	private Integer gamesLost = 0;
	private Integer goalsReceived = 0;
	private Integer goalsMade = 0;

	
	public void increaseGamesWon() {
		gamesWon++;
	}
	
	public void increaseGamesLost() {
		gamesLost++;
	}
	
	public Double getSuccessRate() {
		return 1.0 * gamesWon / (gamesWon + gamesLost);
	}
	
	public Integer getTotalGames() {
		return gamesWon + gamesLost;
	}
	
	public Double getKeeperGoalRate() {
		return 1.0 * goalsReceived / getTotalGames();
	}

}

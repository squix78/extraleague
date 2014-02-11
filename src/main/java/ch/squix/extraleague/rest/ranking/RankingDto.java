package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;

public class RankingDto {
	
	private String player;
	private List<String> badges = new ArrayList<>();
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer ranking;
	
	public String getPlayer() {
		return player;
	}

	public Integer getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(Integer gamesLost) {
		this.gamesLost = gamesLost;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	public void increaseGamesWon() {
		this.gamesWon++;
	}
	
	public void increaseGamesLost() {
		this.gamesLost++;
	}
	
	public Double getSuccessRate() {
		return 1.0 * gamesWon / (gamesWon + gamesLost);
	}

	/**
	 * @return the ranking
	 */
	public Integer getRanking() {
		return ranking;
	}

	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	public List<String> getBadges() {
		return badges;
	}


}

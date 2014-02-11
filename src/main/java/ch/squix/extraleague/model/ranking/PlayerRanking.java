package ch.squix.extraleague.model.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerRanking implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String player;
	private Integer gamesWon = 0;
	private Integer gamesLost = 0;
	private Integer ranking;
	private List<String> badges = new ArrayList<>();
	
	public String getPlayer() {
		return player;
	}
	public Integer getGamesWon() {
		return gamesWon;
	}
	public Integer getGamesLost() {
		return gamesLost;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}
	public void setGamesLost(Integer gamesLost) {
		this.gamesLost = gamesLost;
	}
	
	public Integer getTotalGames() {
		return gamesWon + gamesLost;
	}
	
	/**
	 * @return the badges
	 */
	public List<String> getBadges() {
		return badges;
	}
	/**
	 * @param badges the badges to set
	 */
	public void setBadges(List<String> badges) {
		this.badges = badges;
	}
	
	
	public Double getSuccessRate() {
		return 1.0 * gamesWon / (gamesWon + gamesLost);
	}
	
	public void increaseGamesWon() {
		gamesWon++;
	}
	
	public void increaseGamesLost() {
		gamesLost++;
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

}

package ch.squix.extraleague.rest.ranking;

public class RankingDto {
	
	private String player;
	private Integer gamesWon;
	private Integer gamesLost;
	
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


}

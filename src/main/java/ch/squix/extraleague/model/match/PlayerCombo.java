package ch.squix.extraleague.model.match;

public class PlayerCombo {
	
	private String player;
	private String combo;
	private Integer gamesWon = 0;
	private Integer gamesLost = 0;
	
	public String getPlayer() {
		return player;
	}
	public String getCombo() {
		return combo;
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
	public void setCombo(String combo) {
		this.combo = combo;
	}
	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}
	public void setGamesLost(Integer gamesLost) {
		this.gamesLost = gamesLost;
	}
	
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

}

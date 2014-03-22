package ch.squix.extraleague.rest.result;

public class PlayerScoreDto {
	
	private String player;
	private Integer score = 0;
	private Integer goals = 0;
	
	public PlayerScoreDto() {
		
	}
	
	public PlayerScoreDto(String player, Integer score, Integer goals) {
		this.player = player;
		this.score = score;
		this.goals = goals;
	}
	
	public String getPlayer() {
		return player;
	}
	public Integer getScore() {
		return score;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

}

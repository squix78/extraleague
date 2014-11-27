package ch.squix.extraleague.rest.result;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;

@Embed
public class PlayerScoreDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String player;
	private Integer score = 0;
	private Integer goals = 0;
	private Integer earnedEloPoints = 0;
	
	public PlayerScoreDto() {
		
	}
	
	public PlayerScoreDto(String player, Integer score, Integer goals, Integer earnedEloPoints) {
		this.player = player;
		this.score = score;
		this.goals = goals;
		this.earnedEloPoints = earnedEloPoints;
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

	public Integer getEarnedEloPoints() {
		return earnedEloPoints;
	}

	public void setEarnedEloPoints(Integer earnedEloPoints) {
		this.earnedEloPoints = earnedEloPoints;
	}

}

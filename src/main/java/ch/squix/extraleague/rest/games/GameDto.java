package ch.squix.extraleague.rest.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameDto {

	private Long id;
	private String table;
	private List<String> players = new ArrayList<>();
	
	private Date startDate;
	private Date endDate;
	
	private Double gameProgress;
	
	private Boolean isGameFinished;
	
	private Long estimatedRemainingMilis;
	
	private Integer numberOfCompletedGames;
	private Date firstGoalDate;
	
	public Long getId() {
		return id;
	}

	public String getTable() {
		return table;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the numberOfCompletedGames
	 */
	public Integer getNumberOfCompletedGames() {
		return numberOfCompletedGames;
	}

	/**
	 * @param numberOfCompletedGames the numberOfCompletedGames to set
	 */
	public void setNumberOfCompletedGames(Integer numberOfCompletedGames) {
		this.numberOfCompletedGames = numberOfCompletedGames;
	}

	/**
	 * @return the gameProgress
	 */
	public Double getGameProgress() {
		return gameProgress;
	}

	/**
	 * @param gameProgress the gameProgress to set
	 */
	public void setGameProgress(Double gameProgress) {
		this.gameProgress = gameProgress;
	}

	/**
	 * @return the estimatedTimeOfArrival
	 */
	public Long getEstimatedRemainingMillis() {
		return estimatedRemainingMilis;
	}

	/**
	 * @param estimatedTimeOfArrival the estimatedTimeOfArrival to set
	 */
	public void setEstimatedRemainingMillis(Long estimatedRemainingMilis) {
		this.estimatedRemainingMilis = estimatedRemainingMilis;
	}

	public void setFirstGoalDate(Date firstGoalDate) {
		this.firstGoalDate = firstGoalDate;
	}

	public Date getFirstGoalDate() {
		return firstGoalDate;
	}

	public Boolean isGameFinished() {
		return isGameFinished;
	}

	public void setIsGameFinished(Boolean isGameFinished) {
		this.isGameFinished = isGameFinished;
	}

	
}

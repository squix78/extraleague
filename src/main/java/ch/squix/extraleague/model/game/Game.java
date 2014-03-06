package ch.squix.extraleague.model.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Game {

	@Id
	private Long id;
	
	@Index
	private String table;
	
	
	private List<String> players = new ArrayList<>();
	
	private Date startDate;
	private Date endDate;

	private Integer numberOfCompletedMatches;

	private Double gameProgress;
	
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
	 * @return the numberOfCompletedMatches
	 */
	public Integer getNumberOfCompletedMatches() {
		return numberOfCompletedMatches;
	}


	/**
	 * @param numberOfCompletedMatches the numberOfCompletedMatches to set
	 */
	public void setNumberOfCompletedMatches(Integer numberOfCompletedMatches) {
		this.numberOfCompletedMatches = numberOfCompletedMatches;
	}


	public void setGameProgress(Double gameProgress) {
		this.gameProgress = gameProgress;
	}
	
	public Double getGameProgress() {
		return gameProgress;
	}
	
	
	
	
}

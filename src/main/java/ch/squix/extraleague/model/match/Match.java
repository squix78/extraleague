package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Match { 

	@Id 
	private Long id;
	
	@Index
	private Long gameId;
	private String [] teamA = {};
	private String [] teamB = {};
	private Integer teamAScore;
	private Integer teamBScore;
	
	@Index
	private Date startDate;
	private Date endDate;
	private String table;
	private Integer matchIndex;
	
	@Index
	private List<String> players = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public Long getGameId() {
		return gameId;
	}
	public String[] getTeamA() {
		return teamA;
	}
	public String[] getTeamB() {
		return teamB;
	}
	public Integer getTeamAScore() {
		return teamAScore;
	}
	public Integer getTeamBScore() {
		return teamBScore;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public void setTeamA(String[] teamA) {
		this.teamA = teamA;
	}
	public void setTeamB(String[] teamB) {
		this.teamB = teamB;
	}
	public void setTeamAScore(Integer teamAScore) {
		this.teamAScore = teamAScore;
	}
	public void setTeamBScore(Integer teamBScore) {
		this.teamBScore = teamBScore;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}
	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}
	/**
	 * @return the matchIndex
	 */
	public Integer getMatchIndex() {
		return matchIndex;
	}
	/**
	 * @param matchIndex the matchIndex to set
	 */
	public void setMatchIndex(Integer matchIndex) {
		this.matchIndex = matchIndex;
	}
	/**
	 * @return the players
	 */
	public List<String> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<String> players) {
		this.players = players;
	}

}

package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchDto {
	private Long id; 
	private Long gameId;
	private String [] teamA = {};
	private String [] teamB = {};
	private Integer teamAScore;
	private Integer teamBScore;
	private Date startDate;
	private Date endDate;
	private String table;
	private Integer matchIndex;
	private List<String> scorers = new ArrayList<>();
	private Double winProbabilityTeamA = 0d;
	private Integer winPointsTeamA = 0;
	private Integer winPointsTeamB = 0;
	
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public Integer getMatchIndex() {
		return matchIndex;
	}
	public void setMatchIndex(Integer matchIndex) {
		this.matchIndex = matchIndex;		
	}
	public List<String> getScorers() {
		return scorers;
	}
	public void setScorers(List<String> scorers) {
		this.scorers = scorers;
	}
    public Double getWinProbabilityTeamA() {
        return winProbabilityTeamA;
    }
    public void setWinProbabilityTeamA(Double winProbabilityTeamA) {
        this.winProbabilityTeamA = winProbabilityTeamA;
    }
	public Integer getWinPointsTeamA() {
		return winPointsTeamA;
	}
	public void setWinPointsTeamA(Integer winPointsTeamA) {
		this.winPointsTeamA = winPointsTeamA;
	}
	public Integer getWinPointsTeamB() {
		return winPointsTeamB;
	}
	public void setWinPointsTeamB(Integer winPointsTeamB) {
		this.winPointsTeamB = winPointsTeamB;
	}


}

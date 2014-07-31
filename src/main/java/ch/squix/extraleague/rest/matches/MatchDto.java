package ch.squix.extraleague.rest.matches;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
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
	private Date lastUpdate;
	private Integer maxGoals;


}

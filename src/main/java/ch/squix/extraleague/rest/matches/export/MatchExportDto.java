package ch.squix.extraleague.rest.matches.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.matches.GoalDto;

@Data
public class MatchExportDto {
	
	private String key; 
	private Long gameId;
	private String teamAOffense;
	private String teamADefense;
	private String teamBOffense;
	private String teamBDefense;
	private Integer teamAScore;
	private Integer teamBScore;
	private Date startDate;
	private Date endDate;
	private String table;
	private Integer matchIndex;
	private List<String> scorers = new ArrayList<>();
	private List<GoalDto> goals = new ArrayList<>();
	private Double winProbabilityTeamA = 0d;
	private Integer winPointsTeamA = 0;
	private Integer winPointsTeamB = 0;
	private Integer version;
	private Integer maxGoals;
	private Integer maxMatches;
	private Boolean positionSwappingAllowed;

}

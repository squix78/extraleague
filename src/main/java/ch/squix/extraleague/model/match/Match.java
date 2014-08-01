package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class Match { 

	@Id 
	private Long id;
	
	@Index
	private Long gameId;
	private String [] teamA = {};
	private String [] teamB = {};
	private Integer teamAScore = 0;
	private Integer teamBScore = 0;
	private Double winProbabilityTeamA = 0d;
	private Integer winPointsTeamA = 0;
	private Integer winPointsTeamB = 0;
	
	private List<String> scorers = new ArrayList<>();
	
	@Index
	private List<String> tags = new ArrayList<>();
	
	@Index
	private Date startDate;
	private Date endDate;
	
	@Index
	private String table;
	private Integer matchIndex;
	
	@Index
	private List<String> players = new ArrayList<>();
	
	private Integer maxGoals;
	private Integer maxMatches;


}

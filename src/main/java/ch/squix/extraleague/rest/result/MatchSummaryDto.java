package ch.squix.extraleague.rest.result;

import java.util.List;

import lombok.Data;

@Data
public class MatchSummaryDto {

	private Integer matchIndex;
	private Integer teamAScore;
	private Integer teamBScore;
	private String[] teamA;
	private String[] teamB;
	private List<GoalDto> goals;

}

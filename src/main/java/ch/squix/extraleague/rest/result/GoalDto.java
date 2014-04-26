package ch.squix.extraleague.rest.result;

import lombok.Data;

@Data
public class GoalDto {

	private Integer teamAScore;
	private Integer teamBScore;
	private String scorer;
	private String scorerTeam;

}

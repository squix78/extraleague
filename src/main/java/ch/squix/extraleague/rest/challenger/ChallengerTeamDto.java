package ch.squix.extraleague.rest.challenger;

import java.util.Date;

import lombok.Data;

@Data
public class ChallengerTeamDto {
	
	private Long id;
	
	private Date createdDate;
	
	private String [] challengers;
	
	private String [] winners;

	private String table;

}

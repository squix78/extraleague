package ch.squix.extraleague.rest.matches;

import lombok.Data;

@Data
public class EventDto {
	
	private String player;
	private String goalTime;
	private String message;
	private Integer goalsInARow;

}

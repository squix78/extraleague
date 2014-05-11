package ch.squix.extraleague.rest.playermarket;

import java.util.Date;

import lombok.Data;

@Data
public class MeetingPointPlayerDto {
	
	private Long id;
	private String player;
	private String table;
	private Date availableUntil;

}

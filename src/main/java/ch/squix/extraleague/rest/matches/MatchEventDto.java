package ch.squix.extraleague.rest.matches;

import java.util.Date;

import lombok.Data;

import com.googlecode.objectify.annotation.Embed;

@Data
@Embed
public class MatchEventDto {
	
	private String player;
	private String event;
	private Date time;



}

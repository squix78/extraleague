package ch.squix.extraleague.model.playermarket;

import java.util.Date;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class MeetingPointPlayer {
	
	@Id 
	private Long id;
	private String player;
	private String table;
	
	@Index
	private Date availableUntil;

}

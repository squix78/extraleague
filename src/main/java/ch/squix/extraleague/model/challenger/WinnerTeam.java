package ch.squix.extraleague.model.challenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.games.mode.GameModeEnum;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class WinnerTeam {
	
	@Id
	private Long id;
	
	@Index
	private Date createdDate;
	
	private List<String> winners = new ArrayList<>();
	
	@Index
	private String table;
	
	private GameModeEnum gameMode;

}

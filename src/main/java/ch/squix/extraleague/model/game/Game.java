package ch.squix.extraleague.model.game;

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
public class Game {

	@Id
	private Long id;
	
	@Index
	private String table;
	
	
	private List<String> players = new ArrayList<>();
	
	private Date startDate;
	private Date endDate;
	private Date firstGoalDate;

	private Integer numberOfCompletedMatches;

	private Double gameProgress;
	
	private Boolean isGameFinished;
	
	private GameModeEnum gameMode;
	private Integer maxMatches;
	private Integer maxGoals;

	
	
}

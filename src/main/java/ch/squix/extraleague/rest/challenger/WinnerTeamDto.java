package ch.squix.extraleague.rest.challenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.games.mode.GameModeEnum;

@Data
public class WinnerTeamDto {
	
	private Long id;
	
	private Date createdDate;
	
	private List<String> winners = new ArrayList<>();
	
	private GameModeEnum gameMode;

	private String table;

}

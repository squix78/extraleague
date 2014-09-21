package ch.squix.extraleague.rest.preview;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.games.mode.GameModeEnum;

@Data
public class GamePreviewDto {
	
	private List<String> players = new ArrayList<>();
	private GameModeEnum gameMode;
	private List<MatchPreviewDto> matches = new ArrayList<>();
	

}

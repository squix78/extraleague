package ch.squix.extraleague.rest.preview;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GameDto;
import ch.squix.extraleague.rest.games.mode.GameMode;
import ch.squix.extraleague.rest.games.mode.GameModeFactory;

public class GamePreviewResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamePreviewResource.class.getName());
	
	@Post(value = "json")
	public GamePreviewDto execute(GameDto gameDto) throws UnsupportedEncodingException {
		GameMode mode = GameModeFactory.createGameMode(gameDto.getGameMode());
		
		log.info("Received game to save");
		Game game = new Game();
		game.setPlayers(gameDto.getPlayers());
		game.setStartDate(new Date());
		game.setNumberOfCompletedMatches(0);
		game.setIsGameFinished(false);
		game.setGameMode(gameDto.getGameMode());
		mode.initializeGame(game); 
		
		GamePreviewDto previewDto = new GamePreviewDto();
		previewDto.setPlayers(gameDto.getPlayers());
		List<Match> matches = mode.createMatches(game);
		for (Match match : matches) {
			MatchPreviewDto matchPreviewDto = new MatchPreviewDto();
			matchPreviewDto.setTeamA(match.getTeamA());
			matchPreviewDto.setTeamB(match.getTeamB());
			previewDto.getMatches().add(matchPreviewDto);
		}
		return previewDto;
	}


}
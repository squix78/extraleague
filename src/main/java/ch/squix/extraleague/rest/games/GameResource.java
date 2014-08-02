package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateOpenGamesMessage;



public class GameResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GameResource.class.getName());
	
	@Get(value = "json")
	public GameDto execute() throws UnsupportedEncodingException {
		String gameIdText = (String) this.getRequestAttributes().get("gameId");
		Long gameId = Long.valueOf(gameIdText);
		Game game = ofy().load().type(Game.class).id(gameId).now();
		if (game == null) {
			return null;
		}
		GameDto dto = GameDtoMapper.mapToDto(game);
		return dto;
	}
	
	@Delete(value = "json")
	public void deleteGame() {
		String gameIdText = (String) this.getRequestAttributes().get("gameId");
		Long gameId = Long.valueOf(gameIdText);
		ofy().delete().type(Game.class).id(gameId);
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", gameId).list();
		ofy().delete().entities(matches).now();
		NotificationService.sendMessage(new UpdateOpenGamesMessage(OpenGameService.getOpenGames()));
	}

}

package ch.squix.extraleague.rest.games;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;



public class GameResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GameResource.class.getName());
	
	@Get(value = "json")
	public GameDto execute() throws UnsupportedEncodingException {
		//String table = (String) this.getRequestAttributes().get("table");
		String gameIdText = (String) this.getRequestAttributes().get("gameId");
		Long gameId = Long.valueOf(gameIdText);
		Game game = ofy().load().type(Game.class).id(gameId).now();
		GameDto dto = GameDtoMapper.mapToDto(game);
		return dto;
	}

}

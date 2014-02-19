package ch.squix.extraleague.rest.games;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;

import static com.googlecode.objectify.ObjectifyService.ofy;



public class OpenGamesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public List<GameDto> execute() throws UnsupportedEncodingException {
		String table = (String) this.getRequestAttributes().get("table");
		List<Game> games = ofy().load().type(Game.class).list();
		log.info("Listing table for " + table + ". Found " + games.size() + " former games");
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			if (game.getEndDate() == null) {
				GameDto dto = GameDtoMapper.mapToDto(game);
				gameDtos.add(dto);
			}
		}
		return gameDtos;
	}


}

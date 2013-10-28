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
	public List<GameDto> execute() throws UnsupportedEncodingException {
		String table = (String) this.getRequestAttributes().get("table");
		List<Game> games = ofy().load().type(Game.class).filter("table = ", table).list();
		log.info("Listing table for " + table + ". Found " + games.size() + " former games");
		List<GameDto> gameDtos = new ArrayList<>();
		for (Game game : games) {
			GameDto dto = new GameDto();
			dto.setId(game.getId());
			dto.setPlayers(game.getPlayers());
			dto.setTable(game.getTable());
			dto.setStartDate(game.getStartDate());
			dto.setEndDate(game.getEndDate());
			gameDtos.add(dto);
		}
		return gameDtos;
	}
	
	@Post(value = "json")
	public GameDto create(GameDto dto) {
		log.info("Received game to save");
		Game game = new Game();
		game.setPlayers(dto.getPlayers());
		game.setTable(dto.getTable());
		game.setStartDate(new Date());
		ofy().save().entity(game).now();
		dto.setId(game.getId());
		return dto;
	}

}

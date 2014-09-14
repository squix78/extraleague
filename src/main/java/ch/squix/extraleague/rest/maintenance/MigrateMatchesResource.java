package ch.squix.extraleague.rest.maintenance;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GamesResource;



public class MigrateMatchesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		long t1 = System.currentTimeMillis();
		List<Game> games = ofy().load().type(Game.class).list();
		Map<Long, Game> gameMap = new HashMap<>();
		for (Game game : games) {
			gameMap.put(game.getId(), game);
		}
		List<Match> matches = ofy().load().type(Match.class).list();
		for (Match match: matches) {
			Game game = gameMap.get(match.getGameId());
			if (game!= null) {
				match.setTable(game.getTable());
				match.getTags().add(game.getTable());
			}
		}
		ofy().save().entities(matches);
		return "OK in " + ((System.currentTimeMillis() - t1) / 1000) + "s";
	}
	

}

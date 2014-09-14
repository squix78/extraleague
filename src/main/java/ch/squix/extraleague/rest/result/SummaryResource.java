package ch.squix.extraleague.rest.result;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;

public class SummaryResource extends ServerResource {
	
	@Get(value = "json")
	public SummaryDto execute() throws UnsupportedEncodingException {
		String gameIdText = (String) this.getRequestAttributes().get("gameId");
		Long gameId = Long.valueOf(gameIdText);
		Game game = ofy().load().type(Game.class).id(gameId).now();
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", gameId).list();
		return SummaryService.getSummaryDto(game, matches);
	}


}
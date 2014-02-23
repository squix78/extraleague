package ch.squix.extraleague.rest.maintenance;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.RankingService;
import ch.squix.extraleague.rest.games.GamesResource;



public class MigrateMatchesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		long t1 = System.currentTimeMillis();
		List<Match> matches = ofy().load().type(Match.class).list();
		for (Match match: matches) {
			List<String> players = new ArrayList<>();
			players.addAll(Arrays.asList(match.getTeamA()));
			players.addAll(Arrays.asList(match.getTeamB()));
			match.setPlayers(players);
		}
		ofy().save().entities(matches);
		return "OK in " + ((System.currentTimeMillis() - t1) / 1000) + "s";
	}
	

}

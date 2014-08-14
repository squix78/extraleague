package ch.squix.extraleague.rest.migration;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.games.GamesResource;

import com.google.appengine.api.NamespaceManager;
import com.googlecode.objectify.Key;



public class NamespaceMigrationTaskResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		String sourceNamespace = "";
		String targetNamespace = "nca";
		migrate(sourceNamespace, targetNamespace, Ranking.class);
		migrate(sourceNamespace, targetNamespace, Match.class);
		migrate(sourceNamespace, targetNamespace, Game.class);
		migrate(sourceNamespace, targetNamespace, Mutations.class);
		return "OK";
	}
	
	private void migrate(String sourceNamespace, String targetNamespace, Class type) {
		NamespaceManager.set(sourceNamespace);
		List<Ranking> ranking = ofy().load().type(type).list();
		
		NamespaceManager.set(targetNamespace);
		List<Key<Ranking>> keys = ofy().load().type(type).keys().list();
		ofy().delete().keys(keys).now();
		ofy().save().entities(ranking).now();
	}
}
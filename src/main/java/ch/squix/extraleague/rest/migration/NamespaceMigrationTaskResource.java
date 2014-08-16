package ch.squix.extraleague.rest.migration;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;
import ch.squix.extraleague.rest.games.GamesResource;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;



public class NamespaceMigrationTaskResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		String sourceNamespace = "";
		String targetNamespace = "ncaleague";
//		migrate(sourceNamespace, targetNamespace, Ranking.class);
//		migrate(sourceNamespace, targetNamespace, Match.class);
//		migrate(sourceNamespace, targetNamespace, Game.class);
//		migrate(sourceNamespace, targetNamespace, Mutations.class);
//		migrate(sourceNamespace, targetNamespace, PlayerUser.class);
//		migrate(sourceNamespace, targetNamespace, Statistics.class);
		return "OK";
	}
	
	private void migrate(String sourceNamespace, String targetNamespace, Class type) {
		log.info("Copying type " + type + " from " + sourceNamespace + " to " + targetNamespace);
		NamespaceManager.set(targetNamespace);
		List<Key<Ranking>> keys = ofy().load().type(type).keys().list();
		ofy().delete().keys(keys).now();
		
		NamespaceManager.set(sourceNamespace);
		Query query = ofy().load().type(type).limit(200);
		QueryResultIterator iterator = query.iterator();
		while(true) {
			List items = new ArrayList<>();
			
			while (iterator.hasNext()) {
				items.add(iterator.next());
			}
			if (items.size() == 0) {
				break;
			}
			NamespaceManager.set(targetNamespace);
			ofy().save().entities(items).now();
			NamespaceManager.set(sourceNamespace);
			iterator = query.startAt(iterator.getCursor()).iterator();
		}
	}
}
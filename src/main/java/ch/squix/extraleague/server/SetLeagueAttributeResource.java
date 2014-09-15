package ch.squix.extraleague.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;

import com.google.appengine.api.NamespaceManager;



public class SetLeagueAttributeResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		League currentLeague = LeagueDao.getCurrentLeague();
		if (currentLeague != null) {
			
			currentLeague.setWebhookUrl("https://qrlee.extranet.netcetera.biz/secure/gameFinished/");
			Map<String, String> requestHeaders = new HashMap<>();
			currentLeague.setRequestHeaders(requestHeaders);
			String oldNamespace = NamespaceManager.get();
			try {
				NamespaceManager.set("");
				ofy().save().entity(currentLeague).now();
				return "OK";
			} finally {
			  NamespaceManager.set(oldNamespace);
			}
		}
		return ("No league found");


	}

}

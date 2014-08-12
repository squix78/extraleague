package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.NamespaceManager;

import ch.squix.extraleague.model.mutations.MutationService;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.RankingService;
import ch.squix.extraleague.server.NamespaceFilter;

public class RankingServiceResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(RankingServiceResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		log.info("Calculating ranking for namespace: " + NamespaceManager.get());
		Ranking oldRanking = ofy().load().type(Ranking.class).order("-createdDate").limit(1).first().now();
		Ranking newRanking = RankingService.calculateRankings();
		if (oldRanking != null && newRanking != null) {
			MutationService.calculateMutations(oldRanking, newRanking);
		}
		
		return "OK";
	}


}
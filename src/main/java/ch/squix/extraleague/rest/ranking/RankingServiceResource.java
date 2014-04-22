package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.mutations.MutationService;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.RankingService;

public class RankingServiceResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		Ranking oldRanking = ofy().load().type(Ranking.class).order("-createdDate").limit(1).first().now();
		Ranking newRanking = RankingService.calculateRankings();
		if (oldRanking != null && newRanking != null) {
			MutationService.calculateMutations(oldRanking, newRanking);
		}
		
		return "OK";
	}


}
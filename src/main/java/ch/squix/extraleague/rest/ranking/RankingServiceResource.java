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
		RankingService.calculateRankings();
		List<Ranking> rankings = ofy().load().type(Ranking.class).order("-createdDate").limit(10).list();
		if (rankings.size() >= 2) {
			Ranking newRanking = rankings.get(0);
			Ranking oldRanking = rankings.get(1);
			MutationService.calculateMutations(oldRanking, newRanking);
		}
		
		return "OK";
	}


}
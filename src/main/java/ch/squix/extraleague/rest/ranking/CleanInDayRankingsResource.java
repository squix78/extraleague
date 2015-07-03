package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.Ranking;

public class CleanInDayRankingsResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		List<Ranking> rankings = ofy().load().type(Ranking.class).order("-createdDate").limit(300).list();
		List<Ranking> inTheDayRankings = InTheDayRankingFilter.getInTheDayRankings(rankings);
		ofy().delete().entities(inTheDayRankings).now();
		
		return "OK";
	}


}

package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.RankingService;

public class RankingByTagResource extends ServerResource {
	
	@Get(value = "json")
	public List<RankingDto> execute() throws UnsupportedEncodingException {
		String tag = (String) this.getRequestAttributes().get("tag");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		List<Match> matches = ofy().load().type(Match.class).filter("tags =", tag).filter("startDate > ", calendar.getTime()).list();
		Ranking ranking = RankingService.calculateRankingFromMatches(matches);
		if (ranking == null) {
			return new ArrayList<>();
		}
		return RankingDtoMapper.convertToDto(ranking);
	}




}
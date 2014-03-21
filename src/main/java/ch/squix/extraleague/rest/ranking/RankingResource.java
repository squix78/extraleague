package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class RankingResource extends ServerResource {
	
	@Get(value = "json")
	public List<RankingDto> execute() throws UnsupportedEncodingException {
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		if (ranking == null) {
			return new ArrayList<>();
		}
		return RankingDtoMapper.convertToDto(ranking);
	}




}
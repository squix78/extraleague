package ch.squix.extraleague.rest.player;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;
import ch.squix.extraleague.rest.ranking.RankingsDto;



public class PlayersResource extends ServerResource {
	
	@Get(value = "json")
	public RankingsDto execute() throws UnsupportedEncodingException {
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		List<RankingDto> rankings = RankingDtoMapper.convertToDto(ranking);
		RankingsDto result = new RankingsDto();
		for (RankingDto dto : rankings) {
			result.getRankingMap().put(dto.getPlayer(), dto);
		}
		return result;
	}

}

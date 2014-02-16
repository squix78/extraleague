package ch.squix.extraleague.rest.player;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;



public class PlayersRessource extends ServerResource {
	
	@Get(value = "json")
	public Map<String, RankingDto> execute() throws UnsupportedEncodingException {
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		List<RankingDto> rankings = RankingDtoMapper.convertToDto(ranking);
		Map<String, RankingDto> rankingMap = new HashMap<String, RankingDto>();
		for (RankingDto dto : rankings) {
			rankingMap.put(dto.getPlayer(), dto);
		}
		return rankingMap;
	}

}

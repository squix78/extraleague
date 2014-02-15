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



public class PlayerRessource extends ServerResource {
	
	@Get(value = "json")
	public RankingDto execute() throws UnsupportedEncodingException {
		String player = (String) this.getRequestAttributes().get("player");
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		List<RankingDto> rankings = RankingDtoMapper.convertToDto(ranking);
		for (RankingDto dto : rankings) {
			if (dto.getPlayer().equals(player)) {
				return dto;
			}
		}
		return null;
	}

}

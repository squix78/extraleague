package ch.squix.extraleague.rest.player;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.matches.MatchDto;
import ch.squix.extraleague.rest.matches.MatchDtoMapper;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;



public class PlayerRessource extends ServerResource {
	
	@Get(value = "json")
	public PlayerDto execute() throws UnsupportedEncodingException {
		String player = (String) this.getRequestAttributes().get("player");
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		List<Match> matches = ofy().load().type(Match.class).order("-startDate").filter("players ==", player).limit(8).list();
		List<RankingDto> rankings = RankingDtoMapper.convertToDto(ranking);
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayer(player);
		List<MatchDto> matchDtos = new ArrayList<>();
		for (Match match : matches) {
			MatchDto dto = MatchDtoMapper.mapToDto(match);
			matchDtos.add(dto);
		}
		
		playerDto.setLastMatches(matchDtos);
		for (RankingDto dto : rankings) {
			if (dto.getPlayer().equals(player)) {
				playerDto.setStatistics(dto);
			}
		}
		return playerDto;
	}

}

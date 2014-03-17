package ch.squix.extraleague.rest.player;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
		
		
		List<Match> matches = ofy().load().type(Match.class).order("-startDate").filter("players ==", player).limit(8).list();
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayer(player);
		List<MatchDto> matchDtos = new ArrayList<>();
		for (Match match : matches) {
			MatchDto dto = MatchDtoMapper.mapToDto(match);
			matchDtos.add(dto);
		}
		
		playerDto.setLastMatches(matchDtos);
		
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();

		RankingDto rankingDto = getPlayerRanking(player, ranking);
		playerDto.setStatistics(rankingDto);
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		List<Ranking> todaysRankings = ofy().load().type(Ranking.class).filter("createdDate >", today.getTime()).order("-createdDate").list();
		if (todaysRankings.size() > 1 && matchDtos.size() > 0 && matchDtos.get(0).getStartDate().compareTo(today.getTime()) > 0) {
			Ranking latestRanking = todaysRankings.get(todaysRankings.size() - 1);
			RankingDto dayEndRankingDto = getPlayerRanking(player, latestRanking);
			playerDto.setDayEndStatistics(dayEndRankingDto);
		}
		return playerDto;
	}

	private RankingDto getPlayerRanking(String player, Ranking ranking) {
		List<RankingDto> rankings = RankingDtoMapper.convertToDto(ranking);
		for (RankingDto dto : rankings) {
			if (dto.getPlayer().equals(player)) {
				return dto;
			}
		}
		return null;
	}

}

package ch.squix.extraleague.rest.player;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.EternalRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.matches.MatchDto;
import ch.squix.extraleague.rest.matches.MatchDtoMapper;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;



public class PlayerRessource extends ServerResource {
	
	@Get(value = "json")
	public PlayerDto execute() throws UnsupportedEncodingException {
		String player = (String) this.getRequestAttributes().get("player");
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);

		List<Match> matches = ofy().load().type(Match.class).order("-startDate").filter("players ==", player).limit(8).list();
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayer(player);
		List<MatchDto> matchDtos = new ArrayList<>();
		boolean hadMatchToday = false;
		for (Match match : matches) {
			MatchDto dto = MatchDtoMapper.mapToDto(match);
			matchDtos.add(dto);
			if (dto.getStartDate().compareTo(today.getTime()) > 0) {
				hadMatchToday = true;
			}
		}
		
		playerDto.setLastMatches(matchDtos);
		
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();

		RankingDto rankingDto = RankingDtoMapper.getPlayerRanking(player, ranking);
		playerDto.setStatistics(rankingDto);
		
		Calendar referenceDate = today;
		if (!hadMatchToday) {
			referenceDate = yesterday;
		}
		
		List<Ranking> todaysRankings = ofy().load().type(Ranking.class).filter("createdDate >", referenceDate.getTime()).order("-createdDate").list();
		if (todaysRankings.size() > 1) {
			Ranking latestRanking = todaysRankings.get(todaysRankings.size() - 1);
			RankingDto dayEndRankingDto = RankingDtoMapper.getPlayerRanking(player, latestRanking);
			playerDto.setDayEndStatistics(dayEndRankingDto);
		}
		
		EternalRanking eternalRanking = ofy().load().type(EternalRanking.class).first().now();
		RankingDto eternalPlayerRankings = RankingDtoMapper.getPlayerRanking(player, eternalRanking);
		playerDto.setEternalStatistics(eternalPlayerRankings);
		
		return playerDto;
	}



}

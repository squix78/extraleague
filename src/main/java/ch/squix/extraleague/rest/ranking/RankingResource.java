package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;

public class RankingResource extends ServerResource {
	
	@Get(value = "json")
	public Collection<RankingDto> execute() throws UnsupportedEncodingException {
		List<Match> matches = ofy().load().type(Match.class).list();
		
		Map<String, RankingDto> playerRankingMap = new HashMap<>();
		for (Match match : matches) {
			if (match.getEndDate() != null) {
				String [] winnerTeam = {};
				String [] looserTeam = {};
				if (match.getTeamAScore() > match.getTeamBScore()) {
					winnerTeam = match.getTeamA();
					looserTeam = match.getTeamB();
				} else {
					looserTeam = match.getTeamA();
					winnerTeam = match.getTeamB();	
				}
				for (String player : winnerTeam) {
					RankingDto dto = getRanking(player, playerRankingMap);
					dto.increaseGamesWon();
				}
				for (String player : looserTeam) {
					RankingDto dto = getRanking(player, playerRankingMap);
					dto.increaseGamesLost();
				}
			}
			
		}
		return playerRankingMap.values();
	}
	
	private RankingDto getRanking(String player, Map<String, RankingDto> playerRankingMap) {
		RankingDto ranking = playerRankingMap.get(player);
		if (ranking == null) {
			ranking = new RankingDto();
			ranking.setPlayer(player);
			ranking.setGamesWon(0);
			ranking.setGamesLost(0);
			playerRankingMap.put(player, ranking);
		}
		return ranking;
	}

}
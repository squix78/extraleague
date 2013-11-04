package ch.squix.extraleague.rest.result;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;

public class SummaryResource extends ServerResource {
	
	@Get(value = "json")
	public SummaryDto execute() throws UnsupportedEncodingException {
		String gameIdText = (String) this.getRequestAttributes().get("gameId");
		Long gameId = Long.valueOf(gameIdText);
		Game game = ofy().load().type(Game.class).id(gameId).now();
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", gameId).list();
		SummaryDto dto = new SummaryDto();
		Map<String, Integer> playerScores = new HashMap<>();
		for (String player : game.getPlayers()) {
			playerScores.put(player, 0);
		}
		for (Match match : matches) {
			Integer teamAScore = match.getTeamAScore();
			Integer teamBScore = match.getTeamBScore();
			String [] winners = null;
			if (teamAScore > teamBScore) {
				winners = match.getTeamA();
			} else {
				winners = match.getTeamB();
			}
			for (String winner : winners) {
				Integer score = playerScores.get(winner);
				score++;
				playerScores.put(winner, score);
			}
		}
		for (String player : game.getPlayers()) {
			dto.getPlayerScores().add(new PlayerScoreDto(player, playerScores.get(player)));
		}
		return dto;
	}

}
package ch.squix.extraleague.rest.result;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
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
		Map<String, Integer> playerGoals = new HashMap<>();
		Map<String, Integer> playerEloPoints = new HashMap<>();
		
		for (String player : game.getPlayers()) {
			playerScores.put(player, 0);
			playerGoals.put(player, 0);
			playerEloPoints.put(player, 0);
		}
		for (Match match : matches) {
			Integer teamAScore = match.getTeamAScore();
			Integer teamBScore = match.getTeamBScore();
			String [] winners = null;
			String [] losers = null;
			Integer winPoints = 0;
			if (teamAScore > teamBScore) {
				winners = match.getTeamA();
				losers = match.getTeamB();
				winPoints = match.getWinPointsTeamA();
			} else {
				winners = match.getTeamB();
				losers = match.getTeamA();
				winPoints = match.getWinPointsTeamB();
			}
			for (String winner : winners) {
				Integer score = playerScores.get(winner);
				score++;
				playerScores.put(winner, score);
				Integer eloPoints = playerEloPoints.get(winner);
				playerEloPoints.put(winner, eloPoints + winPoints);
			}
			for (String loser : losers) {
				Integer eloPoints = playerEloPoints.get(loser);
				playerEloPoints.put(loser, eloPoints - winPoints);
			}
			for (String player : playerScores.keySet()) {
				Integer goals = playerGoals.get(player);
				goals += Collections.frequency(match.getScorers(), player);
				playerGoals.put(player, goals);
			}
		}
		for (String player : game.getPlayers()) {
			dto.getPlayerScores().add(new PlayerScoreDto(
					player, playerScores.get(player), 
					playerGoals.get(player), 
					playerEloPoints.get(player)));
		}
		return dto;
	}

}
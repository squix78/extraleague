package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class EloRankingTask implements RankingTask {
	
	private final static Integer INITIAL_RATING = 1000;
	private final static Double K_FACTOR = 16d;

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		for (Map.Entry<String, PlayerRanking> entry : playerRankingMap.entrySet()) {
			entry.getValue().setEloValue(INITIAL_RATING);
		}
		for (Match match : matches.getMatchesSortedByTime()) {
			
			Integer eloTeamA = getEloValue(playerRankingMap, match.getTeamA());
			Integer eloTeamB = getEloValue(playerRankingMap, match.getTeamB());
			Double expectedScore = getExpectedOutcome(eloTeamA, eloTeamB);
			Double scoreTeamA = 1d;
			Double scoreTeamB = 0d;
			if (!MatchUtil.hasTeamAWon(match)) {
				scoreTeamA = 0d;
				scoreTeamB = 1d;
			}
			Integer deltaTeamA = calculateDelta(scoreTeamA, expectedScore);
			Integer deltaTeamB = calculateDelta(scoreTeamB, 1 - expectedScore);
			applyDelta(playerRankingMap, match.getTeamA()[0], deltaTeamA);
			applyDelta(playerRankingMap, match.getTeamA()[1], deltaTeamA);
			applyDelta(playerRankingMap, match.getTeamB()[0], deltaTeamB);
			applyDelta(playerRankingMap, match.getTeamB()[1], deltaTeamB);
			
		}
	}
	
	private void applyDelta(Map<String, PlayerRanking> playerRankingMap, String player, Integer deltaTeam) {
		PlayerRanking playerRanking = playerRankingMap.get(player);
		playerRanking.setEloValue(playerRanking.getEloValue() + deltaTeam);
	}

	private Integer getEloValue(Map<String, PlayerRanking> playerRankingMap, String[] team) {
		Integer eloValuePlayer0 = playerRankingMap.get(team[0]).getEloValue();
		Integer eloValuePlayer1 = playerRankingMap.get(team[1]).getEloValue();
		return (int) Math.round((eloValuePlayer0 + eloValuePlayer1) / 2d);
	}

	public Double getExpectedOutcome(Integer rating, Integer opponentRating) {
		return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - rating) / 400.0)));
	}
	
	public Integer calculateDelta(Double score, Double expectedScore) {
        return (int) (K_FACTOR * (score - expectedScore));
	}

}

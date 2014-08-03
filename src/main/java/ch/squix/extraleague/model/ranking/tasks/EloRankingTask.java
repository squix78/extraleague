package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;

public class EloRankingTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		for (Map.Entry<String, PlayerRanking> entry : playerRankingMap.entrySet()) {
			entry.getValue().setEloValue(EloUtil.INITIAL_RATING);
		}
		for (Match match : matches.getMatchesSortedByTime()) {
			Integer eloTeamA = EloUtil.getEloValue(playerRankingMap, match.getTeamA());
			Integer eloTeamB = EloUtil.getEloValue(playerRankingMap, match.getTeamB());
			Double expectedScore = EloUtil.getExpectedOutcome(eloTeamA, eloTeamB);
			Double scoreTeamA = 1d;
			Double scoreTeamB = 0d;
			if (!MatchUtil.hasTeamAWon(match)) {
				scoreTeamA = 0d;
				scoreTeamB = 1d;
			}
			Integer deltaTeamA = EloUtil.calculateDelta(scoreTeamA, expectedScore);
			Integer deltaTeamB = EloUtil.calculateDelta(scoreTeamB, 1 - expectedScore);
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

}

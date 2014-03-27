package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PlayerGoalsTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		for (String player : matches.getPlayers()) {
			Integer playerGoalSum = 0;
			Integer matchesWithPlayerGoals = 0;
			for (Match match : matches.getMatchesByPlayer(player)) {
				PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
				if (playerMatch.hasPlayerGoals()) {
					playerGoalSum += playerMatch.getPlayerGoals();
					matchesWithPlayerGoals++;
				}
			}
			PlayerRanking playerRanking = playerRankingMap.get(player);
			Double averageGoalsPerGame = 0d;
			if (matchesWithPlayerGoals > 0) {
				averageGoalsPerGame = 1d * playerGoalSum / matchesWithPlayerGoals;
			}
			playerRanking.setAverageGoalsPerGame(averageGoalsPerGame);
		}
	}

}

package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class PlayerGoalsTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		PlayerRanking topShotRanking = null;
		Double maxGoalsPerMatch = 0d;
		for (String player : matches.getPlayers()) {
			Integer playerGoalSum = 0;
			Integer matchesWithPlayerGoals = 0;
			for (Match match : matches.getMatchesByPlayer(player)) {
				PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
				if (playerMatch.getHasPlayerGoals()) {
					playerGoalSum += playerMatch.getPlayerGoals();
					matchesWithPlayerGoals++;
				}
			}
			PlayerRanking playerRanking = playerRankingMap.get(player);
			Double averageGoalsPerMatch = 0d;
			if (matchesWithPlayerGoals > 0) {
				averageGoalsPerMatch = 1d * playerGoalSum / matchesWithPlayerGoals;
				if (averageGoalsPerMatch > maxGoalsPerMatch && matchesWithPlayerGoals >= 8) {
					topShotRanking = playerRanking;
					maxGoalsPerMatch = averageGoalsPerMatch;
				}
			}
			playerRanking.setAverageGoalsPerMatch(averageGoalsPerMatch);
		}
		if (topShotRanking != null) {
			topShotRanking.getBadges().add(BadgeEnum.TopShot.name());
		}
	}

}

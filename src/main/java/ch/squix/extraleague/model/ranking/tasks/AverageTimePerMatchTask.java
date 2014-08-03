package ch.squix.extraleague.model.ranking.tasks;

import java.util.Date;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class AverageTimePerMatchTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		for (String player : matches.getPlayers()) {
			PlayerRanking ranking = playerRankingMap.get(player);
			long totalMatchTime = 0;
			long numberOfMatches = 0;
			for (Match match : matches.getMatchesByPlayer(player)) {
				Date startDate = match.getStartDate();
				Date endDate = match.getEndDate();
				if (startDate != null && endDate != null) {
					totalMatchTime += endDate.getTime() - startDate.getTime();
					numberOfMatches++;
				}
			}
			ranking.setAverageSecondsPerMatch(totalMatchTime / (1000 * numberOfMatches));

		}
	}

}

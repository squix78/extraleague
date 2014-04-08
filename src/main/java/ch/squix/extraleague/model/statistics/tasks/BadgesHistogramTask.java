package ch.squix.extraleague.model.statistics.tasks;

import java.util.HashMap;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public class BadgesHistogramTask implements StatisticTask {

	@Override
	public void calculate(Statistics statistics, Ranking rankings, Matches matches) {
		Map<String, Integer> map = new HashMap<>();

		for (PlayerRanking ranking : rankings.getPlayerRankings()) {
			for (String badge : ranking.getBadges()) {
				Integer badgeCount = map.get(badge);
				if (badgeCount == null) {
					badgeCount = 0;
				}
				map.put(badge, badgeCount + 1);
			}
		}
		statistics.setBadgeHistogram(map);
	}

}

package ch.squix.extraleague.model.statistics.tasks;

import java.util.HashMap;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public class SuccessRateHistogramTask implements StatisticTask {

	private static final Double BIN_COUNT = 20d;

	@Override
	public void calculate(Statistics statistics, Ranking rankings, Matches matches) {
		Map<Integer, Integer> histogram = new HashMap<>();
		for (Integer i = 0; i < BIN_COUNT; i++) {
			histogram.put(i, 0);
		}
		Double binRange = 1 / BIN_COUNT;
		for (PlayerRanking ranking : rankings.getPlayerRankings()) {
			int bin = (int) Math.floor(ranking.getSuccessRate() / binRange);
			Integer binCount = histogram.get(bin);
			if (binCount == null) {
				binCount = 0;
			}
			histogram.put(bin, binCount + 1);
		}
		statistics.setSuccessRateHistogram(histogram);
	}

}

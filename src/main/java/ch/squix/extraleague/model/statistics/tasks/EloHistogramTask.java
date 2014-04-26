package ch.squix.extraleague.model.statistics.tasks;

import java.util.HashMap;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public class EloHistogramTask implements StatisticTask {

	private static final Double BIN_COUNT = 20d;

	@Override
	public void calculate(Statistics statistics, Ranking rankings, Matches matches) {
		Integer minElo = Integer.MAX_VALUE;
		Integer maxElo = Integer.MIN_VALUE;
		for (PlayerRanking ranking : rankings.getPlayerRankings()) {
			minElo = Math.min(minElo, ranking.getEloValue());
			maxElo = Math.max(maxElo, ranking.getEloValue());
		}
		// Ex: maxElo = 1530, minElo = 815, eloRange = 715
		// binRange = 35.75
		Integer eloRange = maxElo - minElo;
		Double binRange = 1d * eloRange / BIN_COUNT;
		Map<Integer, Integer> histogram = new HashMap<>();
		for (Integer i = 0; i < BIN_COUNT; i++) {
			histogram.put(i, 0);
		}
		for (PlayerRanking ranking : rankings.getPlayerRankings()) {
			// eloValue = 1100, bin = (1100 - 815) / 35.75 = 7.9..
			int bin = (int) Math.floor((ranking.getEloValue() - minElo) / binRange);
			Integer binCount = histogram.get(bin);
			if (binCount == null) {
				binCount = 0;
			}
			histogram.put(bin, binCount + 1);
		}
		statistics.setEloHistogramm(histogram);
		statistics.setMinElo(minElo);
		statistics.setMaxElo(maxElo);
		statistics.setEloBinRange(binRange);
	}

}

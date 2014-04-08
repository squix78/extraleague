package ch.squix.extraleague.model.statistics.tasks;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public class SuccessRateHistogramTaskTest {
	
	@Test
	public void shouldCalculateHistogram() {
		
		SuccessRateHistogramTask task = new SuccessRateHistogramTask();
		Statistics statistics = new Statistics();
		Ranking rankings = new Ranking();
		
		rankings.getPlayerRankings().add(createRanking(0.16));
		rankings.getPlayerRankings().add(createRanking(0.99));
		rankings.getPlayerRankings().add(createRanking(0.96));
		task.calculate(statistics, rankings, null);
		
		Map<Integer, Integer> histogram = statistics.getSuccessRateHistogram();
		Assert.assertEquals(Integer.valueOf(1), histogram.get(3));
		Assert.assertEquals(Integer.valueOf(2), histogram.get(19));
	}

	private PlayerRanking createRanking(Double successRate) {
		PlayerRanking ranking = new PlayerRanking();
		int gamesLost = 1000;
		int gamesWon = (int) Math.floor(successRate * gamesLost / (1 - successRate));
		ranking.setGamesWon(gamesWon);
		ranking.setGamesLost(gamesLost);
		return ranking;
	}

}

package ch.squix.extraleague.model.statistics.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public class HourHistogramTask implements StatisticTask {

	@Override
	public void calculate(Statistics statistics, Ranking rankings, Matches matches) {
		Map<Integer, Integer> countMap = new TreeMap<>();
		for (Integer i = 0; i < 24; i++) {
			countMap.put(i, 0);
		}
		Integer totalCountedMatches = 0;
		for (Match match : matches.getMatches()) {
			Date startDate = match.getStartDate();
			if (startDate != null) {
				totalCountedMatches++;
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(startDate);
				Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
				Integer hourCount = countMap.get(hour);
				countMap.put(hour, hourCount + 1);
			}
		}
		Map<Integer, Double> histogram = new TreeMap<>();
		for (Integer hour : countMap.keySet()) {
			Double share = 1d * (countMap.get(hour) / totalCountedMatches);
			histogram.put(hour, share);
		}
		statistics.setHourHistogram(histogram);
	}

}

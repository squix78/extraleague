package ch.squix.extraleague.model.statistics.tasks;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;

public interface StatisticTask {
	
	void calculate(Statistics statistics, Ranking ranking, Matches matches);

}

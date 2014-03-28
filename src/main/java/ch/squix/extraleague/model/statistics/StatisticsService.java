package ch.squix.extraleague.model.statistics;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.tasks.BadgesHistogramTask;
import ch.squix.extraleague.model.statistics.tasks.HourHistogramTask;
import ch.squix.extraleague.model.statistics.tasks.StatisticTask;
import ch.squix.extraleague.model.statistics.tasks.SuccessRateHistogramTask;

public class StatisticsService {
	
	public static void updateStatistics() {
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		List<Match> matchesList = ofy().load().type(Match.class).filter("startDate > ", calendar.getTime()).list();
		Matches matches = new Matches();
		matches.setMatches(matchesList);
		
		Statistics statistics = ofy().load().type(Statistics.class).first().now();
		if (statistics == null) {
			statistics = new Statistics();
		}
		
		List<StatisticTask> statisticTasks = new ArrayList<>();
		statisticTasks.add(new BadgesHistogramTask());
		statisticTasks.add(new HourHistogramTask());
		statisticTasks.add(new SuccessRateHistogramTask());
		
		for (StatisticTask task : statisticTasks) {
			task.calculate(statistics, ranking, matches);
		}
		
		ofy().save().entity(statistics).now();
	}

}

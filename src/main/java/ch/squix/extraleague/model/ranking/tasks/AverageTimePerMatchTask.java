package ch.squix.extraleague.model.ranking.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class AverageTimePerMatchTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date todayStart = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterdayStart = calendar.getTime();
		for (String player : matches.getPlayers()) {
			PlayerRanking ranking = playerRankingMap.get(player);
			long totalMatchTime = 0;
			long numberOfMatches = 0;
			long matchesYesterday = 0;
			long matchesToday = 0;
			long secondsPlayedYesterday = 0;
			long secondsPlayedToday = 0;
			for (Match match : matches.getMatchesByPlayer(player)) {
				Date startDate = match.getStartDate();
				Date endDate = match.getEndDate();
				if (startDate != null && endDate != null) {
					long matchLength = endDate.getTime() - startDate.getTime();
					totalMatchTime += matchLength;
					numberOfMatches++;
					if (startDate.compareTo(yesterdayStart) > 0 && startDate.compareTo(todayStart) < 0) {
						matchesYesterday++;
						secondsPlayedYesterday += matchLength / 1000L;
					}
					if (startDate.compareTo(todayStart) > 0) {
						matchesToday++;
						secondsPlayedToday += matchLength / 1000L;
					}
				}
			}
			ranking.setAverageSecondsPerMatch(totalMatchTime / (1000 * numberOfMatches));
			ranking.setMatchesPlayedYesterday(matchesYesterday);
			ranking.setMatchesPlayedToday(matchesToday);
			ranking.setSecondsPlayedYesterday(secondsPlayedYesterday);
			ranking.setSecondsPlayedToday(secondsPlayedToday);

		}
	}

}

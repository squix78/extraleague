package ch.squix.extraleague.model.ranking.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
		calendar.set(Calendar.MILLISECOND, 0);
		Date todayStart = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterdayStart = calendar.getTime();
		for (String player : matches.getPlayers()) {
			PlayerRanking ranking = playerRankingMap.get(player);
			long totalMatchTime = 0;
			long numberOfMatches = 0;
			Map<Date, Long> matchesPlayedPerDay = new HashMap<Date, Long>();
			Map<Date, Long> secondsPlayedPerDay = new HashMap<Date, Long>();
			for (Match match : matches.getMatchesByPlayer(player)) {
				Date startDate = match.getStartDate();
				Date endDate = match.getEndDate();
				if (startDate != null && endDate != null) {
					long matchLength = endDate.getTime() - startDate.getTime();
					totalMatchTime += matchLength;
					numberOfMatches++;
					Date day = getDayStart(startDate);
					Long matchesPlayed = matchesPlayedPerDay.get(day);
					if (matchesPlayed == null) {
						matchesPlayed = 0L;
					}
					matchesPlayed++;
					matchesPlayedPerDay.put(day, matchesPlayed);
					Long secondsPlayed = secondsPlayedPerDay.get(day);
					if (secondsPlayed == null) {
						secondsPlayed = 0L;
					}
					secondsPlayed += matchLength / 1000;
					secondsPlayedPerDay.put(day, secondsPlayed);
					
					
				}
			}
			ranking.setAverageSecondsPerMatch(totalMatchTime / (1000 * numberOfMatches));
			ranking.setMatchesPlayedPerDay(matchesPlayedPerDay);
			ranking.setSecondsPlayedPerDay(secondsPlayedPerDay);
		}
	}
	
	private static Date getDayStart(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}

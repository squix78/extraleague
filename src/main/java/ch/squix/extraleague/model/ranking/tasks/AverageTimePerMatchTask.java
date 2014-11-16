package ch.squix.extraleague.model.ranking.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;

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
			Map<Date, Long> matchesPlayedPerDay = createInitializedMap();
			Map<Date, Long> secondsPlayedPerDay = createInitializedMap();

			for (Match match : matches.getMatchesByPlayer(player)) {
				Date startDate = match.getStartDate();
				Date endDate = match.getEndDate();
				if (startDate != null && endDate != null) {
					long matchLength = endDate.getTime() - startDate.getTime();
					totalMatchTime += matchLength;
					numberOfMatches++;
					Date day = LocalDate.fromDateFields(startDate).toDate();
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
	
	private Map<Date, Long> createInitializedMap() {
		LocalDate date = new LocalDate();
		date = date.minusDays(30);
		Map<Date, Long> map = new HashMap<>();
		for (int i = 0; i < 31; i++) {
			map.put(date.toDate(), 0L);
			date = date.plusDays(1);
		}
		return map;
		
	}

}

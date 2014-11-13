package ch.squix.extraleague.model.ranking.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;

public class EloRankingTask implements RankingTask {

	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		for (Map.Entry<String, PlayerRanking> entry : playerRankingMap.entrySet()) {
			entry.getValue().setEloValue(EloUtil.INITIAL_RATING);
		}
		Map<String, Map<Date, Long>> playerEloPerDay = new HashMap<>();
		for (Match match : matches.getMatchesSortedByTime()) {
			Integer eloTeamA = EloUtil.getEloValue(playerRankingMap, match.getTeamA());
			Integer eloTeamB = EloUtil.getEloValue(playerRankingMap, match.getTeamB());
			Double expectedScore = EloUtil.getExpectedOutcome(eloTeamA, eloTeamB);
			Double scoreTeamA = 1d;
			Double scoreTeamB = 0d;
			if (!MatchUtil.hasTeamAWon(match)) {
				scoreTeamA = 0d;
				scoreTeamB = 1d;
			}
			Integer deltaTeamA = EloUtil.calculateDelta(scoreTeamA, expectedScore);
			Integer deltaTeamB = EloUtil.calculateDelta(scoreTeamB, 1 - expectedScore);
			if (match.getStartDate() != null) {
				Date day = getDayStart(match.getStartDate());
				applyDelta(playerRankingMap, playerEloPerDay, day, match.getTeamA()[0], deltaTeamA);
				applyDelta(playerRankingMap, playerEloPerDay, day, match.getTeamA()[1], deltaTeamA);
				applyDelta(playerRankingMap, playerEloPerDay, day, match.getTeamB()[0], deltaTeamB);
				applyDelta(playerRankingMap, playerEloPerDay, day, match.getTeamB()[1], deltaTeamB);
			}

		}
	}

	private void applyDelta(Map<String, PlayerRanking> playerRankingMap, Map<String, Map<Date, Long>> playerEloPerDay, Date day, String player, Integer deltaTeam) {
		PlayerRanking playerRanking = playerRankingMap.get(player);
		playerRanking.setEloValue(playerRanking.getEloValue() + deltaTeam);
		Map<Date, Long> eloPerDay = playerEloPerDay.get(player);
		if (eloPerDay == null) {
			eloPerDay = new HashMap<>();
			playerEloPerDay.put(player, eloPerDay);
		}
		Long elo = eloPerDay.get(day);
		if (elo == null) {
			elo = 0L;
		}
		elo += deltaTeam;
		eloPerDay.put(day, elo);
		playerRanking.setEloPerDay(eloPerDay);
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

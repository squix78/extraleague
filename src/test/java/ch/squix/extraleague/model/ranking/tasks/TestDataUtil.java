package ch.squix.extraleague.model.ranking.tasks;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class TestDataUtil {
	

	public static Map<String, PlayerRanking> createPlayerRankingMap(String ...players) {
		Map<String, PlayerRanking> rankingMap = new HashMap<>();
		for (String player : players) {
			PlayerRanking playerRanking = new PlayerRanking();
			playerRanking.setPlayer(player);
			rankingMap.put(player, playerRanking);
		}
		return rankingMap;
	}

	public static Match createMatch(int teamAScore, int teamBScore, String ...players) {
		Match match = new Match();
		match.setTeamA(new String[] {players[0], players[1]});
		match.setTeamB(new String[] {players[2], players[3]});
		match.setTeamAScore(teamAScore);
		match.setTeamBScore(teamBScore);
		match.setEndDate(new Date());
		return match;
	}

}

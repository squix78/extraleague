package ch.squix.extraleague.model.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;

public class RankingService {

	public static void calculateRankings() {
		List<Match> matches = ofy().load().type(Match.class).list();

		Map<String, PlayerRanking> playerRankingMap = new HashMap<>();
		for (Match match : matches) {
		        //clearWrongCharacters(match);
			if (match.getEndDate() != null) {
				String[] winnerTeam = {};
				String[] looserTeam = {};
				if (match.getTeamAScore() > match.getTeamBScore()) {
					winnerTeam = match.getTeamA();
					looserTeam = match.getTeamB();
				} else {
					looserTeam = match.getTeamA();
					winnerTeam = match.getTeamB();
				}
				for (String player : winnerTeam) {
					PlayerRanking playerRanking = getRanking(player, playerRankingMap);
					playerRanking.increaseGamesWon();
				}
				for (String player : looserTeam) {
					PlayerRanking playerRanking  = getRanking(player, playerRankingMap);
					playerRanking.increaseGamesLost();
				}
				calculateMatchBadges(match, playerRankingMap);
			}

		}
		//ofy().save().entities(matches);
		List<PlayerRanking> rankings = filterFirstPlayers(playerRankingMap.values());
		Collections.sort(rankings, new Comparator<PlayerRanking>() {

			@Override
			public int compare(PlayerRanking o1, PlayerRanking o2) {
				int result = o2.getSuccessRate().compareTo(o1.getSuccessRate());
				if (result == 0) {
					return o2.getTotalGames().compareTo(o1.getTotalGames());
				}
				return result;
			}
			
		});
		int index = 1;
		for (PlayerRanking ranking : rankings) {
			ranking.setRanking(index);
			index++;
		}
		calculateBadges(rankings);
		Ranking ranking = new Ranking();
		ranking.setCreatedDate(new Date());
		ranking.setPlayerRankings(rankings);
		ofy().save().entities(ranking);

	}

	private static List<PlayerRanking> filterFirstPlayers(Collection<PlayerRanking> values) {
		List<PlayerRanking> rankings = new ArrayList<>();
		for (PlayerRanking ranking : values) {
			if (ranking.getTotalGames() >=8) {
				rankings.add(ranking);
			}
		}
		return rankings;
	}

    private static void calculateMatchBadges(Match match, Map<String, PlayerRanking> playerRankingMap) {
		if (match.getTeamAScore() == 5 && match.getTeamBScore() == 0) {
			for (String player : match.getTeamA()) {
				addFiveZeroBadge(playerRankingMap, player);
			}
		}
		if (match.getTeamBScore() == 5 && match.getTeamAScore() == 0) {
			for (String player : match.getTeamB()) {
				addFiveZeroBadge(playerRankingMap, player);
			}
		}
	}

	private static void addFiveZeroBadge(Map<String, PlayerRanking> playerRankingMap, String player) {
		PlayerRanking ranking = getRanking(player, playerRankingMap);
		if (!ranking.getBadges().contains("5:0!")) {
			ranking.getBadges().add("5:0!");
		}
	}

	private static void calculateBadges(List<PlayerRanking> rankings) {
		for (PlayerRanking ranking : rankings) {
			if (ranking.getRanking() == 1) {
				ranking.getBadges().add("King");
			}
			if (ranking.getRanking() == 2) {
				ranking.getBadges().add("Queen");
			}
			if (ranking.getRanking() == 3) {
				ranking.getBadges().add("Rook");
			}
			if (ranking.getRanking() == 4) {
				ranking.getBadges().add("Bishop");
			}
			if (ranking.getRanking() == rankings.size()) {
				ranking.getBadges().add("Pawn");
			}
		}
	}

	private static PlayerRanking getRanking(String player, Map<String, PlayerRanking> playerRankingMap) {
		PlayerRanking ranking = playerRankingMap.get(player);
		if (ranking == null) {
			ranking = new PlayerRanking();
			ranking.setPlayer(player);
			ranking.setGamesWon(0);
			ranking.setGamesLost(0);
			playerRankingMap.put(player, ranking);
		}
		return ranking;
	}

}

package ch.squix.extraleague.model.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
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

		Map<Long, List<Match>> gameMap = new HashMap<>();
		for (Match match : matches) {
			List<Match> gameMatches = gameMap.get(match.getGameId());
			if (gameMatches == null) {
				gameMatches = new ArrayList<>();
				gameMap.put(match.getGameId(), gameMatches);
			}
			gameMatches.add(match);
		}
		Map<String, PlayerRanking> playerRankingMap = new HashMap<>();
		
		for (Map.Entry<Long, List<Match>> entry : gameMap.entrySet()) {
			Map<String, List<String>> scoreMap = new HashMap<>();
			Map<String, Integer> winMap = new HashMap<>();
			for (Match match : entry.getValue()) {
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
						Integer wins = winMap.get(player);
						if (wins == null) {
							wins = 0;
						}
						wins++;
						winMap.put(player, wins);
					}
					for (String player : looserTeam) {
						PlayerRanking playerRanking  = getRanking(player, playerRankingMap);
						playerRanking.increaseGamesLost();
					}
					addMatchScore(scoreMap, match.getTeamA(), match.getTeamAScore(), match.getTeamBScore());
					addMatchScore(scoreMap, match.getTeamB(), match.getTeamBScore(), match.getTeamAScore());
				}
			}
			calculateMatchBadges(scoreMap, playerRankingMap);
			addStrikeBadge(winMap, playerRankingMap);
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

	private static void addStrikeBadge(Map<String, Integer> winMap, Map<String, PlayerRanking> playerRankingMap) {
		for (Map.Entry<String, Integer> entry : winMap.entrySet()) {
			if (entry.getValue() == 4) {
				PlayerRanking ranking = getRanking(entry.getKey(), playerRankingMap);
				ranking.getBadges().add("Strike");
			}
		}
	}

	private static void addMatchScore(Map<String, List<String>> scoreMap, String [] players, Integer teamScore, Integer oppositeTeamScore) {
		for (String player : players) {
			List<String> score = scoreMap.get(player);
			if (score == null) {
				score = new ArrayList<>();
				scoreMap.put(player, score);
			}
			score.add(teamScore + ":" + oppositeTeamScore);
		}
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

    private static void calculateMatchBadges(Map<String, List<String>> scoreMap, Map<String, PlayerRanking> playerRankingMap) {
    	 for(String player : scoreMap.keySet()) {
    		 List<String> scores = scoreMap.get(player);
    		 int numberOfFiveZeros = Collections.frequency(scores, "5:0");
    		 if (numberOfFiveZeros > 0) {
	    		 PlayerRanking ranking = getRanking(player, playerRankingMap);
	    		 ranking.getBadges().add(numberOfFiveZeros + "x5:0!");
    		 }
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

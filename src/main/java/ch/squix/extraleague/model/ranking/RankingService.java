package ch.squix.extraleague.model.ranking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.PlayerMatchResult;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
				    List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
				    for (PlayerMatchResult playerMatch : playerMatches) {
				        PlayerRanking playerRanking = getRanking(playerMatch.getPlayer(), playerRankingMap);
				        if (playerMatch.hasWon()) {
				            playerRanking.increaseGamesWon();
				            Integer matchesWon = winMap.get(playerRanking.getPlayer());
				            if (matchesWon == null) {
				                matchesWon = 0;
				            }
				            winMap.put(playerRanking.getPlayer(), matchesWon + 1);

				        } else {
				            playerRanking.increaseGamesLost();
				        }
				        playerRanking.setGoalsMade(playerRanking.getGoalsMade() + playerMatch.getGoalsMade());
				        playerRanking.setGoalsGot(playerRanking.getGoalsGot() + playerMatch.getGoalsGot());
				        addMatchScore(scoreMap, playerMatch);
				    }

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
					return o2.getGoalRate().compareTo(o1.getGoalRate());
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
	private static void addMatchScore(Map<String, List<String>> scoreMap, PlayerMatchResult playerMatch) {
			List<String> score = scoreMap.get(playerMatch.getPlayer());
			if (score == null) {
				score = new ArrayList<>();
				scoreMap.put(playerMatch.getPlayer(), score);
			}
			score.add(playerMatch.getGoalsMade() + ":" + playerMatch.getGoalsGot());
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

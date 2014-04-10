package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class SpecialResultPerGameTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {

    	Map<String, Integer> maxFiveZeroPerPlayerMap = new HashMap<>();
    	Map<String, Integer> max5To4PerPlayerMap = new HashMap<>();
        for (Map.Entry<Long, List<Match>> entry : matches.getGameMatches().entrySet()) {
        	Map<String, List<String>> scoreMap = new HashMap<>();
        	Map<String, Integer> comebackMap = new HashMap<>();
	        for (Match match : entry.getValue()) {
	        	
	            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
	            for (PlayerMatchResult playerMatch : playerMatches) {
	            	List<String> playerScores = scoreMap.get(playerMatch.getPlayer());
	            	if (playerScores == null) {
	            		playerScores = new ArrayList<>();
	            		scoreMap.put(playerMatch.getPlayer(), playerScores);
	            	}
	            	playerScores.add(playerMatch.getGoalsMade() + ":" + playerMatch.getGoalsGot());
	            	List<String> inBetweenScores = playerMatch.getInBetweenScores();
	            	if (inBetweenScores.contains("0:4") && inBetweenScores.contains("5:4")) {
	            		Integer comebackCount = comebackMap.get(playerMatch.getPlayer());
	            		if (comebackCount == null) {
	            			comebackCount = 0;
	            		}
	            		comebackCount++;
	            		comebackMap.put(playerMatch.getPlayer(), comebackCount);
	            	}
	            }
	        }
	        for (String player : scoreMap.keySet()) {
	        	calculateMaxSpecialResultPerGame(maxFiveZeroPerPlayerMap, scoreMap, player, "5:0");
	        	calculateMaxSpecialResultPerGame(max5To4PerPlayerMap, scoreMap, player, "5:4");
	        }
	        for (String player : comebackMap.keySet()) {
	        	Integer comebackCount = comebackMap.get(player);
	        	PlayerRanking playerRanking = playerRankingMap.get(player);
	        	if (comebackCount == 1) {
	        		playerRanking.getBadges().add(BadgeEnum.BruceWillis.name());
	        	} else if (comebackCount >= 2) {
	        		playerRanking.getBadges().add(BadgeEnum.ChuckNorris.name());
	        	}
	        }
	        
        }
        for(String player : maxFiveZeroPerPlayerMap.keySet()) {
                Integer fiveZeroes = maxFiveZeroPerPlayerMap.get(player);
                PlayerRanking ranking = playerRankingMap.get(player);
                if (fiveZeroes != null && fiveZeroes > 0) {
                	BadgeEnum badge = null;
                	if (fiveZeroes == 1) {
                		badge = BadgeEnum.Shutout;
                	} else if (fiveZeroes == 2) {
                		badge = BadgeEnum.BackToBackShutout;
                	} else if (fiveZeroes == 3) {
                		badge = BadgeEnum.IAmLegend;
                	} else if (fiveZeroes == 4) {
                		badge = BadgeEnum.NoMercy;
                	}
                	ranking.getBadges().add(badge.name());
                }
                Integer fiveToFours = max5To4PerPlayerMap.get(player);
                if (fiveToFours != null && fiveToFours == 4) {
                	ranking.getBadges().add(BadgeEnum.IceMan.name());
                }
        }
    }

	private void calculateMaxSpecialResultPerGame(Map<String, Integer> maxSpecialResultMap, Map<String, List<String>> scoreMap,
			String player, String specialResult) {
		Integer maxFiveZero = maxSpecialResultMap.get(player);
		if (maxFiveZero == null) {
			maxFiveZero = 0;
		}
		maxFiveZero = Math.max(maxFiveZero, Collections.frequency(scoreMap.get(player), specialResult));
		maxSpecialResultMap.put(player, maxFiveZero);
	}

}

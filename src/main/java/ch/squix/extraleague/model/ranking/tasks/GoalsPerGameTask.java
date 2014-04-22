package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class GoalsPerGameTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	Integer allPlayersMaxGoalsPerGame = 0;
        for (Map.Entry<Long, List<Match>> entry : matches.getGameMatches().entrySet()) {
        	Map<String, Integer> playerGoalsPerGame = new HashMap<>();
	        for (Match match : entry.getValue()) {
	        	
	            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
	            for (PlayerMatchResult playerMatch : playerMatches) {
	            	Integer totalGoalsPerGame = playerGoalsPerGame.get(playerMatch.getPlayer());
	            	if (totalGoalsPerGame == null) {
	            		totalGoalsPerGame = 0;
	            	}
	            	Integer playerGoals = playerMatch.getPlayerGoals();
	            	if (playerGoals == null) {
	            		playerGoals = 0;
	            	}
	            	playerGoalsPerGame.put(playerMatch.getPlayer(), totalGoalsPerGame + playerGoals);
	            }
	        }
	        for (String player : playerGoalsPerGame.keySet()) {
	        	PlayerRanking ranking = playerRankingMap.get(player);
	        	Integer maxGoalsPerGame = ranking.getMaxGoalsPerGame();
	        	if (maxGoalsPerGame == null) {
	        		maxGoalsPerGame = 0;
	        	}
	        	Integer goalsPerGame = playerGoalsPerGame.get(player);
				if (goalsPerGame > maxGoalsPerGame ) {
	        		ranking.setMaxGoalsPerGame(goalsPerGame);
	        	}
				if (goalsPerGame > allPlayersMaxGoalsPerGame) {
					allPlayersMaxGoalsPerGame = goalsPerGame;
				}
	        }
	        
        }
        List<PlayerRanking> playerRankings = new ArrayList<>();
        playerRankings.addAll(playerRankingMap.values());
        Collections.sort(playerRankings, new MaxGoalsPerGameComparator());
        for (PlayerRanking ranking : playerRankings) {
        	if (ranking.getMaxGoalsPerGame() == allPlayersMaxGoalsPerGame) {
        		ranking.getBadges().add(BadgeEnum.JohnWayne.name());
        	}
        }

    }
    
    private static class MaxGoalsPerGameComparator implements Comparator<PlayerRanking> {

		@Override
		public int compare(PlayerRanking o1, PlayerRanking o2) {
			Integer maxGoalsPerGame1 = o1.getMaxGoalsPerGame();
			Integer maxGoalsPerGame2 = o2.getMaxGoalsPerGame();
			if (maxGoalsPerGame2 != null) {
				return maxGoalsPerGame2.compareTo(maxGoalsPerGame1);
			}
			return maxGoalsPerGame1;
		}
    	
    }


}

package ch.squix.extraleague.model.ranking.tasks;

import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.match.Position;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class ProjectLeaderTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	for (Match match : matches.getMatches()) {
    		List<PlayerMatchResult> matchResults = MatchUtil.getPlayerMatchResults(match);
    		for (PlayerMatchResult matchResult : matchResults) {
    			if (matchResult.isWon() && matchResult.getPlayerGoals() != null && matchResult.getPlayerGoals() == 0 && matchResult.getPosition() == Position.Offensive) {
    				PlayerRanking playerRanking = playerRankingMap.get(matchResult.getPlayer());
    				playerRanking.getBadges().add(BadgeEnum.PL.name());
    			}
    			if (matchResult.isWon() 
    					&& matchResult.getPlayerGoals() != null 
    					&& matchResult.getPlayerGoals() == match.getMaxGoals() 
    					&& matchResult.getPosition() == Position.Defensive) {
    				PlayerRanking playerRanking = playerRankingMap.get(matchResult.getPlayer());
    				playerRanking.getBadges().add(BadgeEnum.TC.name());
    			}
    		}
    			
    	}

    }

}

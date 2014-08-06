package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class AchievementPointTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	for (PlayerRanking ranking : playerRankingMap.values()) {
    		for (String badgeName : ranking.getBadges()) {
    			BadgeEnum badge = BadgeEnum.getBadgeByName(badgeName);
    			if (badge != null) {
    				ranking.addAchievementPoints(badge.getAchievementPoints());
    			}
    		}

    	}
    	
    	
	}

}

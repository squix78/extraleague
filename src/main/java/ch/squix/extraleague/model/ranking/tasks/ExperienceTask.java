package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class ExperienceTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	for (PlayerRanking ranking : playerRankingMap.values()) {
    		Integer totalGames = ranking.getTotalGames();
    		if (totalGames != null) {
    			BadgeEnum badge = null;
    			if (totalGames < 10) {
    				badge = BadgeEnum.Rookie;
    			} else if (totalGames < 20) {
    				badge = BadgeEnum.Private;
    			} else if (totalGames < 40) {
    				badge = BadgeEnum.Corporal;
    			} else if (totalGames < 80) {
    				badge = BadgeEnum.Seargant;
    			} else if (totalGames < 160) {
    				badge = BadgeEnum.FirstLeutenant;
    			} else if (totalGames >= 320) {
    				badge = BadgeEnum.Captain;
    			}
    			ranking.getBadges().add(badge.name());
    		}
    	}
    }

}

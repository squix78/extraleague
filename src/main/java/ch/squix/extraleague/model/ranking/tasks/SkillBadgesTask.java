package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;
import java.util.logging.Logger;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class SkillBadgesTask implements RankingTask {
	
	private static final Logger log = Logger.getLogger(SkillBadgesTask.class.getName());

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	PlayerRanking bestOffensivePlayer = null;
    	Double bestOffensiveRate = 0d;
    	PlayerRanking bestDefensivePlayer = null;
    	Double bestDefensiveRate = 0d;
    	PlayerRanking mostBalancedPlayer = null;
    	Double lowestDelta = 100d;
        for (String player : playerRankingMap.keySet()) {
        	PlayerRanking ranking = playerRankingMap.get(player);
        	if (ranking.getOffensivePositionRate() > bestOffensiveRate) {
        		bestOffensivePlayer = ranking;
        		bestOffensiveRate = ranking.getOffensivePositionRate();
        	}
        	if (ranking.getDefensivePositionRate() > bestDefensiveRate) {
        		bestDefensivePlayer = ranking;
        		bestDefensiveRate = ranking.getDefensivePositionRate();
        	}
        	Double delta = Math.abs(ranking.getOffensivePositionRate() - ranking.getDefensivePositionRate());
        	if (delta < lowestDelta) {
        		mostBalancedPlayer = ranking;
        		lowestDelta = delta;
        	}
        }
        if (bestOffensivePlayer != null) {
        	bestOffensivePlayer.getBadges().add(BadgeEnum.BattleAxe.name());
        	log.info("Player " + bestOffensivePlayer.getPlayer() + " has best offensive rate");
        }
        if (bestDefensivePlayer != null) {
        	bestDefensivePlayer.getBadges().add(BadgeEnum.TheWall.name());
        	log.info("Player " + bestDefensivePlayer.getPlayer() + " has best defensive rate");
        }
        if (mostBalancedPlayer != null) {
        	mostBalancedPlayer.getBadges().add(BadgeEnum.ZenMonk.name());
        	log.info("Player " + mostBalancedPlayer.getPlayer() + " has most balanced skills");
        }
    }

}

package ch.squix.extraleague.model.ranking.tasks;

import java.util.Date;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class SkillBadgesTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	PlayerRanking bestOffensivePlayer = null;
    	Double bestOffensiveRate = 0d;
    	PlayerRanking bestDefensivePlayer = null;
    	Double bestDefensiveRate = 0d;
    	PlayerRanking mostBalancedPlayer = null;
    	Double lowestDelta = 100d;
        for (String player : matches.getPlayers()) {
        	PlayerRanking ranking = playerRankingMap.get(player);
        	if (ranking.getOffensivePositionRate() > bestOffensiveRate) {
        		bestOffensivePlayer = ranking;
        	}
        	if (ranking.getDefensivePositionRate() > bestDefensiveRate) {
        		bestDefensivePlayer = ranking;
        	}
        	Double delta = Math.abs(ranking.getOffensivePositionRate() - ranking.getDefensivePositionRate());
        	if (delta < lowestDelta) {
        		mostBalancedPlayer = ranking;
        	}
        }
        if (bestOffensivePlayer != null) {
        	bestOffensivePlayer.getBadges().add(BadgeEnum.BattleAxe.name());
        }
        if (bestDefensivePlayer != null) {
        	bestDefensivePlayer.getBadges().add(BadgeEnum.TheWall.name());
        }
        if (mostBalancedPlayer != null) {
        	mostBalancedPlayer.getBadges().add(BadgeEnum.ZenMonk.name());
        }
    }

}

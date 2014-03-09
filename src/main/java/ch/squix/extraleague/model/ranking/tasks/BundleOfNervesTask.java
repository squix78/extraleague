package ch.squix.extraleague.model.ranking.tasks;

import java.util.Date;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class BundleOfNervesTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	Double maxRate = 0d;
    	PlayerRanking nervousPlayerRanking = null;
        for (String player : matches.getPlayers()) {
        	PlayerRanking ranking = playerRankingMap.get(player);
        	int numberOfFourToFiveMatches = 0;
        	int numberOfMatches = 0;
        	for (Match match : matches.getMatchesByPlayer(player)) {
        		PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
        		if (playerMatch.getGoalsGot() == 5 && playerMatch.getGoalsMade() == 4) {
        			numberOfFourToFiveMatches++;
        		}
        		numberOfMatches++;
        	}
        	Double rate = 1d * numberOfFourToFiveMatches / numberOfMatches;
        	if (rate > maxRate) {
        		nervousPlayerRanking = ranking;
        	}
        }
        if (nervousPlayerRanking != null) {
        	nervousPlayerRanking.getBadges().add(BadgeEnum.BundleOfNerves.name());
        }
    }

}

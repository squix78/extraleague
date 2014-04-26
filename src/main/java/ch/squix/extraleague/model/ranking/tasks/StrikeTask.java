package ch.squix.extraleague.model.ranking.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class StrikeTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        
        Map<Long, List<Match>> gameMatches = matches.getGameMatches();
        for (Map.Entry<Long, List<Match>> gameEntry : gameMatches.entrySet()) {
        	Map<String, Integer> winMap = new HashMap<>();
            for (Match match : gameEntry.getValue()) {
                List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
                for (PlayerMatchResult playerMatch : playerMatches) {
                    if (playerMatch.isWon()) {
                        Integer wins = winMap.get(playerMatch.getPlayer());
                        if (wins == null) {
                            wins = 0;
                        }
                        wins++;
                        winMap.put(playerMatch.getPlayer(), wins);
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : winMap.entrySet()) {
            	if (entry.getValue() == 4) {
            		PlayerRanking playerRanking = playerRankingMap.get(entry.getKey());
            		playerRanking.getBadges().add(BadgeEnum.Strike.name());
            	}
            }
        }
    }

}

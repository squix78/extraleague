package ch.squix.extraleague.model.ranking.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class FiveZeroTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        Map<String, Integer> scoreMap = new HashMap<String, Integer>();
        for (Match match : matches.getMatches()) {
            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
            for (PlayerMatchResult playerMatch : playerMatches) {
                Integer fiveZeroCount = scoreMap.get(playerMatch.getPlayer());
                if (fiveZeroCount == null) {
                        fiveZeroCount = 0;
                }
                fiveZeroCount++;
                scoreMap.put(playerMatch.getPlayer(), fiveZeroCount);
            }
        }
        for(String player : scoreMap.keySet()) {
                Integer fiveZeroes = scoreMap.get(player);
                if (fiveZeroes > 0) {
                    PlayerRanking ranking = playerRankingMap.get(player);
                    ranking.getBadges().add(fiveZeroes + "x5:0!");
                }
        }
    }

}

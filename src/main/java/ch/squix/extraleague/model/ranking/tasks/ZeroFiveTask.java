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


public class ZeroFiveTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        Map<String, List<String>> scoreMap = new HashMap<String, List<String>>();
        for (Match match : matches.getMatches()) {
            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
            for (PlayerMatchResult playerMatch : playerMatches) {
                List<String> score = scoreMap.get(playerMatch.getPlayer());
                if (score == null) {
                        score = new ArrayList<>();
                        scoreMap.put(playerMatch.getPlayer(), score);
                }
                score.add(playerMatch.getGoalsMade() + ":" + playerMatch.getGoalsGot());
            }
        }
        for(String player : scoreMap.keySet()) {
                List<String> scores = scoreMap.get(player);
                int numberOfFiveZeros = Collections.frequency(scores, "5:0");
                if (numberOfFiveZeros > 0) {
                    PlayerRanking ranking = playerRankingMap.get(player);
                    ranking.getBadges().add(numberOfFiveZeros + "x5:0!");
                }
        }
    }

}

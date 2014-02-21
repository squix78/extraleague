package ch.squix.extraleague.model.ranking.tasks;

import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class ScoreTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        for (Match match : matches.getMatches()) {
            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
            for (PlayerMatchResult playerMatch : playerMatches) {
                PlayerRanking playerRanking = playerRankingMap.get(playerMatch.getPlayer());
                if (playerMatch.hasWon()) {
                    playerRanking.increaseGamesWon();
                } else {
                    playerRanking.increaseGamesLost();
                }
                playerRanking.setGoalsMade(playerRanking.getGoalsMade() + playerMatch.getGoalsMade());
                playerRanking.setGoalsGot(playerRanking.getGoalsGot() + playerMatch.getGoalsGot());
            }
        }
    }

}

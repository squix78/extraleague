package ch.squix.extraleague.model.ranking.tasks;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchDateComparator;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class SlamTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        MatchDateComparator matchDateComparator = new MatchDateComparator();
        for (String player : matches.getPlayers()) {
                PlayerRanking ranking = playerRankingMap.get(player);
                int victoriesInARow = 0;
                int maxVictoriesInARow = 0;
                List<Match> matchesByPlayer = matches.getMatchesByPlayer(player);
                Collections.sort(matchesByPlayer, matchDateComparator);
                
                for (Match match : matchesByPlayer) {
                        PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
                        if (playerMatch.isWon()) {
                                victoriesInARow++;
                        } else {
                                victoriesInARow = 0;
                        }
                        if (victoriesInARow > maxVictoriesInARow) {
                        	maxVictoriesInARow = victoriesInARow;
                        }
                }

                if (maxVictoriesInARow > 4) {
                        ranking.getBadges().add(maxVictoriesInARow + "xSlam");
                }
        }
    }

}

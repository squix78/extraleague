package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public interface RankingTask {
    
    /**
     * Ranks matches under specific aspects.
     * @param matches
     */
    void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches);

}

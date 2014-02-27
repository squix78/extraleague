package ch.squix.extraleague.model.ranking.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class CurrentShapeTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        
    	for (String player : matches.getPlayers()) {
    		PlayerRanking ranking = playerRankingMap.get(player);
    		List<Match> allMatches = matches.getMatchesByPlayer(player);
    		Integer fromIndex = Math.max(allMatches.size() - 8, 0);
    		List<Match> lastEightMatches = allMatches.subList(fromIndex, allMatches.size());
    		Integer gamesWon = 0;
    		for (Match match : lastEightMatches) {
    			PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
    			if (playerMatch.hasWon()) {
    				gamesWon++;
    			}
    		}
    		Double currentShapeRate = 1.0 * gamesWon / lastEightMatches.size();
    		ranking.setCurrentShapeRate(currentShapeRate);
    	}
    }

}

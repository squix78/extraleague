package ch.squix.extraleague.model.ranking.tasks;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class IncestuousTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        
    	for (String player : matches.getPlayers()) {
    		PlayerRanking ranking = playerRankingMap.get(player);
    		List<Match> allMatches = matches.getMatchesByPlayer(player);
    		Integer fromIndex = Math.max(allMatches.size() - 8, 0);
    		List<Match> lastEightMatches = allMatches.subList(fromIndex, allMatches.size());
    		Set<String> playerSet = new HashSet<>();
    		for (Match match : lastEightMatches) {
    			playerSet.addAll(match.getPlayers());
    		}
    		if (playerSet.size() == 4 && allMatches.size() > 1) {
    			ranking.getBadges().add(BadgeEnum.Incestuous.name());
    		}
    	}
    }

}

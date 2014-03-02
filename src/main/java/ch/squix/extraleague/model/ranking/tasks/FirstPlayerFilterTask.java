package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class FirstPlayerFilterTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
		for (PlayerRanking ranking : rankings) {
			if (ranking.getTotalGames() <8) {
				playerRankingMap.remove(ranking.getPlayer());
			}
		}

    }
    

}

package ch.squix.extraleague.model.ranking.tasks;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class RankingIndexTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        setSuccessRateRankingIndex(playerRankingMap);
        setDynamicRankingIndex(playerRankingMap);
		calculateBadges(playerRankingMap);
    }

    private void setSuccessRateRankingIndex(Map<String, PlayerRanking> playerRankingMap) {
        List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
        Collections.sort(rankings, new Comparator<PlayerRanking>() {

            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                int result = o2.getSuccessRate().compareTo(o1.getSuccessRate());
                if (result == 0) {
                    return o2.getGoalRate().compareTo(o1.getGoalRate());
                }
                return result;
            }

        });
        int index = 1;
        for (PlayerRanking ranking : rankings) {
            ranking.setRanking(index);
            index++;
        }
    }

    private void setDynamicRankingIndex(Map<String, PlayerRanking> playerRankingMap){
        SortedSet<PlayerRanking> sortedPlayerRanking = getSortedPlayerRanking(playerRankingMap.values());
        int i = 1;
        for (PlayerRanking playerRanking : sortedPlayerRanking) {
            playerRanking.setDynamicRanking(i++);
        }
    }

    private SortedSet<PlayerRanking> getSortedPlayerRanking(Collection<PlayerRanking> playerRankings) {
        TreeSet<PlayerRanking> sortedPlayerRankings = new TreeSet<>(new Comparator<PlayerRanking>() {
            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                int result = o2.getRankingPoints().compareTo(o1.getRankingPoints());
                if (result == 0) {
                    return o2.getGoalRate().compareTo(o1.getGoalRate());
                }
                return result;
            }
        });
        sortedPlayerRankings.addAll(playerRankings);
        return sortedPlayerRankings;
    }

	private void calculateBadges(Map<String, PlayerRanking> playerRankingMap) {
        Collection<PlayerRanking> rankings = playerRankingMap.values();
        for (PlayerRanking ranking : rankings) {
			if (ranking.getRanking() == 1) {
				ranking.getBadges().add(BadgeEnum.King.name());
			}
			if (ranking.getRanking() == 2) {
				ranking.getBadges().add(BadgeEnum.Queen.name());
			}
			if (ranking.getRanking() == rankings.size()) {
				ranking.getBadges().add(BadgeEnum.Pawn.name());
			}
		}
	}

}

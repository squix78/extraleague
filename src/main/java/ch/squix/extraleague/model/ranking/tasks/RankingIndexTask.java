package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class RankingIndexTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
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
		calculateBadges(rankings);
    }

	private void calculateBadges(List<PlayerRanking> rankings) {
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

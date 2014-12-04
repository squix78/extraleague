package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class PlayerOfTheDayTask implements RankingTask {


	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
		if (rankings.size() > 0) {
			Collections.sort(rankings, new DeltaComparator(true));
			Integer minElo = rankings.get(0).getEloDelta();
			Integer maxElo = rankings.get(rankings.size() - 1).getEloDelta();
			for (PlayerRanking ranking : rankings) {
				Integer eloDelta = ranking.getEloDelta();
				if (maxElo != null && maxElo > 0 && eloDelta != null && maxElo == eloDelta) {
					ranking.getBadges().add(BadgeEnum.EloWinnerOfTheDay.name());
				}
				if (minElo != null && minElo < 0 && eloDelta != null && minElo == eloDelta) {
					ranking.getBadges().add(BadgeEnum.EloLoserOfTheDay.name());
				}
			}
			Collections.sort(rankings, new DeltaComparator(false));
			Integer minRanking = rankings.get(0).getRankingDelta();
			Integer maxRanking = rankings.get(rankings.size() - 1).getRankingDelta();
			for (PlayerRanking ranking : rankings) {
				Integer rankingDelta = ranking.getRankingDelta();
				if (maxRanking != null && maxRanking > 0 && rankingDelta != null && maxRanking == rankingDelta) {
					ranking.getBadges().add(BadgeEnum.RankWinnerOfTheDay.name());
				}
				if (minRanking != null && minRanking < 0 && rankingDelta != null && minRanking == rankingDelta) {
					ranking.getBadges().add(BadgeEnum.RankLoserOfTheDay.name());
				}
			}
		}
		
	}
	
	private class DeltaComparator implements Comparator<PlayerRanking> {

		private Boolean compareElo;

		public DeltaComparator(Boolean compareElo) {
			this.compareElo = compareElo;
		}
		
		@Override
		public int compare(PlayerRanking o1, PlayerRanking o2) {
			if (o1 != null && o2 != null) {
				Integer delta1 = null; o1.getEloDelta();
				Integer delta2 = null; o2.getEloDelta();
				if (compareElo) {
					delta1 = o1.getEloDelta();
					delta2 = o2.getEloDelta();
				} else {
					delta1 = o1.getRankingDelta();
					delta2 = o2.getRankingDelta();					
				}
				if (delta1 == null) {
					delta1 = 0;
				} 
				if (delta2 == null) {
					delta2 = 0;
				}
				return delta1.compareTo(delta2);
			}
			return 0;
		}
		
	}

}

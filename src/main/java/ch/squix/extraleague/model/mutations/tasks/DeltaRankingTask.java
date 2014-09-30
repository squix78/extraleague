package ch.squix.extraleague.model.mutations.tasks;

import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class DeltaRankingTask implements MutationTask {

	@Override
	public void calculate(Map<String, PlayerMutation> mutationMap, Ranking oldRanking, Ranking newRanking) {

		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {
			PlayerRanking oldPlayerRanking = oldRanking.getPlayerRanking(newPlayerRanking.getPlayer());
			Integer newRank = newPlayerRanking.getEloRanking();
			if (oldPlayerRanking != null) {
				Integer oldRank = oldPlayerRanking.getEloRanking();
				newPlayerRanking.setRankingDelta(oldRank - newRank);
			}
		}


		
	}

}

package ch.squix.extraleague.model.mutations.tasks;

import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class TopTenTask implements MutationTask {

	@Override
	public void calculate(Map<String, PlayerMutation> mutationMap, Ranking oldRanking, Ranking newRanking) {

		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {
			PlayerRanking oldPlayerRanking = oldRanking.getPlayerRanking(newPlayerRanking.getPlayer());
			Integer newRank = newPlayerRanking.getRanking();
			Integer oldRank = 999;
			if (oldPlayerRanking != null) {
				oldRank = oldPlayerRanking.getRanking();
			}
			if (oldRank > 10 && newRank <= 10) {
				PlayerMutation playerMutation = MutationUtil.getOrCreatePlayerMutation(mutationMap, newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Climbed to top 10! Welcome to world class");
			}
			if (oldRank <= 10 && newRank > 10) {
				PlayerMutation playerMutation = MutationUtil.getOrCreatePlayerMutation(mutationMap, newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Relegated from world class. Goodbye top 10!");
			}
		}


		
	}

}

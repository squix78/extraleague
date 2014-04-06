package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

import com.google.common.base.Joiner;

public class BadgeMutationsTask implements MutationTask {

	@Override
	public void calculate(Mutations mutations, Ranking oldRanking, Ranking newRanking) {

		Joiner joiner = Joiner.on(", ").skipNulls();
		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {
			PlayerRanking oldPlayerRanking = oldRanking.getPlayerRanking(newPlayerRanking.getPlayer());
			List<String> newBadges = new ArrayList<>();
			List<String> lostBadges = new ArrayList<>();
			newBadges.addAll(newPlayerRanking.getBadges());
			if (oldPlayerRanking != null) {
				newBadges.removeAll(oldPlayerRanking.getBadges());
				lostBadges.addAll(oldPlayerRanking.getBadges());
				lostBadges.removeAll(newPlayerRanking.getBadges());
			}
			if (newBadges.size() > 0) {
				String newBadgeText = joiner.join(newBadges);
				mutations.getPlayerMutations().add(new PlayerMutation(
						newPlayerRanking.getPlayer(), 
						newBadgeText, 
						"New Badges earned: " + newBadgeText));
			}
			if (lostBadges.size() > 0) {
				String lostBadgeText = joiner.join(lostBadges);
				mutations.getPlayerMutations().add(new PlayerMutation(
						newPlayerRanking.getPlayer(), 
						lostBadgeText, 
						"Badges lost: " + lostBadgeText));
			}
		}

		
	}

}

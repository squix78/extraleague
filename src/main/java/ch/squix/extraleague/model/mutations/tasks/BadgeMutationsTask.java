package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

import com.google.common.base.Joiner;

public class BadgeMutationsTask implements MutationTask {

	@Override
	public List<PlayerMutation> calculate(Ranking oldRanking, Ranking newRanking) {
		List<PlayerMutation> mutations = new ArrayList<>();
		Joiner joiner = Joiner.on(" ").skipNulls();
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
				PlayerMutation playerMutation = new PlayerMutation(newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Badges earned: " + newBadgeText);
				mutations.add(playerMutation);
			}
			if (lostBadges.size() > 0) {
				String lostBadgeText = joiner.join(lostBadges);
				PlayerMutation playerMutation = new PlayerMutation(newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Badges lost: " + lostBadgeText);
				mutations.add(playerMutation);
			}
		}
		return mutations;

		
	}


}

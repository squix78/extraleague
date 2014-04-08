package ch.squix.extraleague.model.mutations.tasks;

import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class CoronationTask implements MutationTask {

	@Override
	public void calculate(Map<String, PlayerMutation> mutationMap, Ranking oldRanking, Ranking newRanking) {

		String oldKing = "";
		String newKing = "";
		for (PlayerRanking oldPlayerRanking : oldRanking.getPlayerRankings()) {
			if (oldPlayerRanking.getBadges().contains(BadgeEnum.King.name())) {
				oldKing = oldPlayerRanking.getPlayer();
			}
		}
		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {
			if (newPlayerRanking.getBadges().contains(BadgeEnum.King.name())) {
				newKing = newPlayerRanking.getPlayer();
			}
		}
		if (oldKing != null && newKing != null && !newKing.equals(oldKing)) {
				PlayerMutation playerMutation = mutationMap.get(newKing);
				if (playerMutation == null) {
					playerMutation = new PlayerMutation(newKing);
					mutationMap.put(newKing, playerMutation);
				}
				playerMutation.getDescriptions().add(
						"The king is dead! Long live the King! " 
							+ oldKing + " has been purged from the throne by " + newKing);
		}

		
	}

}

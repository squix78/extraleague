package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

import com.google.common.base.Joiner;

public class CoronationTask implements MutationTask {

	@Override
	public void calculate(Mutations mutations, Ranking oldRanking, Ranking newRanking) {

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
			mutations.getPlayerMutations().add(
					new PlayerMutation(newKing, oldKing, "The king is dead! Long live the King! " 
							+ oldKing + " has been purged from the throne by " + newKing));
		}

		
	}

}

package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class TopTenTask implements MutationTask {

	@Override
	public List<PlayerMutation> calculate(Ranking oldRanking, Ranking newRanking) {
		List<PlayerMutation> mutations = new ArrayList<>();
		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {
			PlayerRanking oldPlayerRanking = oldRanking.getPlayerRanking(newPlayerRanking.getPlayer());
			Integer newRank = newPlayerRanking.getEloRanking();
			Integer oldRank = 999;
			if (oldPlayerRanking != null) {
				oldRank = oldPlayerRanking.getEloRanking();
			}
			if (oldRank > 10 && newRank <= 10) {
				PlayerMutation playerMutation = new PlayerMutation(newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Climbed to top 10! Welcome to world class");
				mutations.add(playerMutation);
			}
			if (oldRank <= 10 && newRank > 10) {
				PlayerMutation playerMutation = new PlayerMutation(newPlayerRanking.getPlayer());
				playerMutation.getDescriptions().add("Relegated from world class. Goodbye top 10!");
				mutations.add(playerMutation);
			}
		}
		return mutations;


		
	}

}

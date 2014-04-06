package ch.squix.extraleague.model.mutations.tasks;

import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.Ranking;


public interface MutationTask {
	
	void calculate(Map<String, PlayerMutation> mutationMap, Ranking oldRanking, Ranking newRanking);

}

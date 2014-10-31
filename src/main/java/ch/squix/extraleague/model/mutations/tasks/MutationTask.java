package ch.squix.extraleague.model.mutations.tasks;

import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.Ranking;


public interface MutationTask {
	
	List<PlayerMutation> calculate(Ranking oldRanking, Ranking newRanking);

}

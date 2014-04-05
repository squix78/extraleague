package ch.squix.extraleague.model.mutations.tasks;

import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.Ranking;

public interface MutationTask {
	
	void calculate(Mutations mutation, Ranking oldRanking, Ranking newRanking);

}

package ch.squix.extraleague.model.mutations;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.mutations.tasks.MutationTask;
import ch.squix.extraleague.model.ranking.Ranking;

public class MutationService {
	
	public static Mutations calculateMutations(Ranking oldRanking, Ranking newRanking) {
		Mutations mutations = new Mutations();
		
		List<MutationTask> mutationTasks = new ArrayList<>();
		for (MutationTask task : mutationTasks) {
			task.calculate(mutations, oldRanking, newRanking);
		}
		
		return mutations;
	}

}

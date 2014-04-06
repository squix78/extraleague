package ch.squix.extraleague.model.mutations;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.mutations.tasks.BadgeMutationsTask;
import ch.squix.extraleague.model.mutations.tasks.CoronationTask;
import ch.squix.extraleague.model.mutations.tasks.MutationTask;
import ch.squix.extraleague.model.mutations.tasks.TopTenTask;
import ch.squix.extraleague.model.ranking.Ranking;

public class MutationService {
	
	public static Mutations calculateMutations(Ranking oldRanking, Ranking newRanking) {
		Mutations mutations = ofy().load().type(Mutations.class).first().now();
		if (mutations == null) {
			mutations = new Mutations();
		}
		List<MutationTask> mutationTasks = new ArrayList<>();
		//mutationTasks.add(new PromotedRelegatedPlayersTask());
		mutationTasks.add(new BadgeMutationsTask());
		mutationTasks.add(new CoronationTask());
		mutationTasks.add(new TopTenTask());
		
		Map<String, PlayerMutation> mutationMap = new HashMap<>();
		for (MutationTask task : mutationTasks) {
			task.calculate(mutationMap, oldRanking, newRanking);
		}
		mutations.getPlayerMutations().addAll(mutationMap.values());
		// Limit the persisted mutations
		while (mutations.getPlayerMutations().size() > 100) {
			mutations.getPlayerMutations().remove(0);
		}
		ofy().save().entities(mutations);
		return mutations;
	}

}

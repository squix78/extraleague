package ch.squix.extraleague.model.mutations;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.mutations.tasks.BadgeMutationsTask;
import ch.squix.extraleague.model.mutations.tasks.CoronationTask;
import ch.squix.extraleague.model.mutations.tasks.MutationTask;
import ch.squix.extraleague.model.mutations.tasks.TopTenTask;
import ch.squix.extraleague.model.ranking.Ranking;

public class MutationService {
	
	public static void calculateMutations(Ranking oldRanking, Ranking newRanking) {

		List<MutationTask> mutationTasks = new ArrayList<>();
		//mutationTasks.add(new PromotedRelegatedPlayersTask());
		mutationTasks.add(new BadgeMutationsTask());
		mutationTasks.add(new CoronationTask());
		mutationTasks.add(new TopTenTask());
		
		List<PlayerMutation> mutations = new ArrayList<>();
		for (MutationTask task : mutationTasks) {
			mutations.addAll(task.calculate(oldRanking, newRanking));
		}
		Collection<PlayerMutation> mergedMutations = mergePlayerMutations(mutations);

		ofy().save().entities(mergedMutations).now();

	}

	private static Collection<PlayerMutation> mergePlayerMutations(List<PlayerMutation> mutations) {
		Map<String, PlayerMutation> playerMutationMap = new HashMap<>();
		for (PlayerMutation mutation: mutations) {
			PlayerMutation existingMutation = playerMutationMap.get(mutation.getPlayersKey());
			if (existingMutation == null) {
				playerMutationMap.put(mutation.getPlayersKey(), mutation);
			} else {
				existingMutation.getDescriptions().addAll(mutation.getDescriptions());
			}
		}
		return playerMutationMap.values();
	}


}

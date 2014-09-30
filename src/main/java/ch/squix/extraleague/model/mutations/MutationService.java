package ch.squix.extraleague.model.mutations;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.squix.extraleague.model.mutations.tasks.BadgeMutationsTask;
import ch.squix.extraleague.model.mutations.tasks.CoronationTask;
import ch.squix.extraleague.model.mutations.tasks.DeltaRankingTask;
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
		mutationTasks.add(new DeltaRankingTask());
		
		Map<String, PlayerMutation> mutationMap = new HashMap<>();
		for (MutationTask task : mutationTasks) {
			task.calculate(mutationMap, oldRanking, newRanking);
		}
		Set<String> existingKeys = new HashSet<>();
		List<PlayerMutation> newPlayerMutations = new ArrayList<>();
		List<PlayerMutation> allMutations = new ArrayList<>();
		allMutations.addAll(mutations.getPlayerMutations());
		allMutations.addAll(mutationMap.values());
		
		for (PlayerMutation playerMutation : allMutations) {
			String key = playerMutation.toString();
			if (!existingKeys.contains(key)) {
				existingKeys.add(key);
				newPlayerMutations.add(playerMutation);
			}
		}
		// Limit the persisted mutations
		while (newPlayerMutations.size() > 100) {
			newPlayerMutations.remove(0);
		}
		mutations.setPlayerMutations(newPlayerMutations);
		ofy().save().entities(mutations).now();
		return mutations;
	}

}

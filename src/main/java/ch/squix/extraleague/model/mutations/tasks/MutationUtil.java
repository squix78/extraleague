package ch.squix.extraleague.model.mutations.tasks;

import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;

public class MutationUtil {
	
	public static PlayerMutation getOrCreatePlayerMutation(Map<String, PlayerMutation> mutationMap, String player) {
		PlayerMutation mutation = mutationMap.get(player);
		if (mutation == null) {
			mutation = new PlayerMutation(player);
			mutationMap.put(player, mutation);
		}
		return mutation;
	}

}

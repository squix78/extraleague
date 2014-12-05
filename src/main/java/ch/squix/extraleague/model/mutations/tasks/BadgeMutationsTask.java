package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.squix.extraleague.model.mutations.BadgeMutation;
import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class BadgeMutationsTask implements MutationTask {

	@Override
	public List<PlayerMutation> calculate(Ranking oldRanking, Ranking newRanking) {
		List<PlayerMutation> mutations = new ArrayList<>();
		PlayerMutation mutation = new PlayerMutation();
		Set<String> mutatedPlayers = new HashSet<>();
		for (PlayerRanking newPlayerRanking : newRanking.getPlayerRankings()) {

			
			PlayerRanking oldPlayerRanking = oldRanking.getPlayerRanking(newPlayerRanking.getPlayer());
			List<String> newBadges = new ArrayList<>();
			List<String> lostBadges = new ArrayList<>();
			newBadges.addAll(newPlayerRanking.getBadges());
			if (oldPlayerRanking != null) {
				newBadges.removeAll(oldPlayerRanking.getBadges());
				lostBadges.addAll(oldPlayerRanking.getBadges());
				lostBadges.removeAll(newPlayerRanking.getBadges());
			}
			if (newBadges.size() > 0) {
				mutatedPlayers.add(newPlayerRanking.getPlayer());
				BadgeMutation badgeMutation = new BadgeMutation();
				badgeMutation.setPlayer(newPlayerRanking.getPlayer());
				badgeMutation.getBadges().addAll(newBadges);
				mutation.getWonBadges().add(badgeMutation);
			}
			if (lostBadges.size() > 0) {
				mutatedPlayers.add(newPlayerRanking.getPlayer());
				BadgeMutation badgeMutation = new BadgeMutation();
				badgeMutation.setPlayer(newPlayerRanking.getPlayer());
				badgeMutation.getBadges().addAll(lostBadges);
				mutation.getLostBadges().add(badgeMutation);
			}
		}
		if (mutation.getWonBadges().size() > 0 || mutation.getLostBadges().size() > 0) {
			mutation.setPlayers(new ArrayList<>(mutatedPlayers));
			mutations.add(mutation);
		}
		return mutations;

		
	}


}

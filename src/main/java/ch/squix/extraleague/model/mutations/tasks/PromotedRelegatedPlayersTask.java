package ch.squix.extraleague.model.mutations.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class PromotedRelegatedPlayersTask implements MutationTask {

	@Override
	public void calculate(Mutations mutations, Ranking oldRanking, Ranking newRanking) {
		Map<String, Integer> oldPlayerRankMap = new HashMap<>();
		for (PlayerRanking playerRanking : oldRanking.getPlayerRankings()) {
			oldPlayerRankMap.put(playerRanking.getPlayer(), playerRanking.getRanking());
		}
		List<PlayerDelta> playerDeltas = new ArrayList<>();
		List<PlayerDelta> newcomerDeltas = new ArrayList<>();
		for (PlayerRanking playerRanking : newRanking.getPlayerRankings()) {
			Integer oldRank = oldPlayerRankMap.get(playerRanking.getPlayer());
			Integer newRank = playerRanking.getRanking();
			if (oldRank == null) {
				newcomerDeltas.add(new PlayerDelta(playerRanking.getPlayer(), newRank));
			} else {
				playerDeltas.add(new PlayerDelta(playerRanking.getPlayer(), oldRank - newRank));
			}
		}
		PlayerDeltaComparator comparator = new PlayerDeltaComparator();
	    Collections.sort(playerDeltas, comparator);
	    Collections.sort(newcomerDeltas, comparator);
	    PlayerDelta promotedPlayer = playerDeltas.get(playerDeltas.size() - 1);
	    mutations.getPlayerMutations().add(
	    		new PlayerMutation(promotedPlayer.player, String.valueOf(promotedPlayer.deltaRank), 
	    				"Most promoted: " + promotedPlayer.player + " climbed " + promotedPlayer.deltaRank + " ranks"));
	    
	    PlayerDelta relegatedPlayer = playerDeltas.get(0);
	    mutations.getPlayerMutations().add(
	    		new PlayerMutation(relegatedPlayer.player, String.valueOf(relegatedPlayer.deltaRank), 
	    				"Most promoted: " + relegatedPlayer.player + " fell " + relegatedPlayer.deltaRank + " ranks"));
	    
	    if (newcomerDeltas.size() > 0) {
	    	PlayerDelta bestNewcomer = newcomerDeltas.get(0);
	    	mutations.getPlayerMutations().add(
	    			new PlayerMutation(bestNewcomer.player, String.valueOf(bestNewcomer.deltaRank), 
	    					"Best newcomer: " + bestNewcomer.player + " made it directly to rank " + bestNewcomer.deltaRank));
	    }
		
	}
	
	private static class PlayerDelta {
		private String player;
		private Integer deltaRank;
		
		PlayerDelta(String player, Integer deltaRank) {
			this.player = player;
			this.deltaRank = deltaRank;
		}
	}
	
	private static class PlayerDeltaComparator implements Comparator<PlayerDelta> {

		@Override
		public int compare(PlayerDelta o1, PlayerDelta o2) {
			return o1.deltaRank.compareTo(o2.deltaRank);
		}
		
	}

}

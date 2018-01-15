package ch.squix.extraleague.rest.games.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.squix.extraleague.model.ranking.Ranking;

public class FourMatchesToFiveModeRandom extends FourMatchesToFiveMode {

	@Override
	public List<String> sortPlayersByRanking(List<String> players, Ranking currentRanking) {
		List<String> rankList = super.sortPlayersByRanking(players, currentRanking);

		List<String> startList = new ArrayList<>();
		Random random = new Random();
		Integer firstPlayer = random.nextInt(4);
		startList.add(rankList.get(firstPlayer));
		// Two places possible for the second ranked player, randomize those
		Integer secondPlayer = (firstPlayer + 2) % 4;
		if (random.nextInt(2) == 1) {
			secondPlayer = 3 - secondPlayer;
		}
		startList.add(rankList.get(secondPlayer));
		// Third player starts opposite second ranked player (3 - secondPlayers position)
		startList.add(rankList.get(3 - secondPlayer));
		// Fourth player starts opposite first ranked player (3 - firstPlayers position)
		startList.add(rankList.get(3 - firstPlayer));
		return startList;
	}


}

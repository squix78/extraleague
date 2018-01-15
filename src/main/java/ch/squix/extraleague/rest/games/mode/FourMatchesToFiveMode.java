package ch.squix.extraleague.rest.games.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class FourMatchesToFiveMode implements GameMode {
	
	private static final Integer [][] mutations = {{0,1,2,3}, {1, 2, 3, 0}, {2, 0, 3, 1}, {0, 3, 1, 2}};
	
	private static final Integer MAX_GOALS = 5;

	private static final Integer MAX_MATCHES = 4;
	


	@Override
	public void initializeGame(Game game) {
		game.setMaxMatches(MAX_MATCHES);
		game.setMaxGoals(MAX_GOALS);
	}

	@Override
	public List<Match> createMatches(Game game) {
	    Ranking currentRanking = ofy().load().type(Ranking.class).order("-createdDate").first().now();

		List<String> players = game.getPlayers();
		if (currentRanking != null) {
				players = sortPlayersByRanking(players, currentRanking);
		}
		List<Match> matches = new ArrayList<>();
		System.out.println("GameID: " + game.getId());

		for (int matchIndex = 0; matchIndex < 4; matchIndex++) {
			Integer [] mutation = mutations[matchIndex];
			Match match = new Match();
			
			match.setTeamA(new String[] {players.get(mutation[0]), players.get(mutation[1])});
			match.setTeamB(new String[] {players.get(mutation[2]), players.get(mutation[3])});
			match.setMaxGoals(MAX_GOALS);
			match.setMaxMatches(MAX_MATCHES);
			match.setPlayers(players);
			match.setTable(game.getTable());
			match.getTags().add(game.getTable());
			match.setMatchIndex(matchIndex);
			match.setPositionSwappingAllowed(false);
			EloUtil.setEloValuesToMatch(match, currentRanking);
			matches.add(match);
		}
		return matches;
	}


	public List<String> sortPlayersByRanking(List<String> players, Ranking currentRanking) {
		Map<Integer, String> playerRankMap = new TreeMap<>();
		Integer newPlayerRank = Integer.MAX_VALUE - 10;
		for (String player : players) {
			Optional<PlayerRanking> playerRanking = currentRanking.getPlayerRanking(player);
			Integer rank;
			if (playerRanking.isPresent()) {
				rank = playerRanking.get().getEloRanking();
			} else {
				rank = newPlayerRank++;
			}
			playerRankMap.put(rank, player);
		}
		return new ArrayList<>(playerRankMap.values());
	}
}

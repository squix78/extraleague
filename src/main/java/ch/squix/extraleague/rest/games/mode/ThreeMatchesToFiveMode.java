package ch.squix.extraleague.rest.games.mode;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.objectify.Key;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;

/**
 * In this game mode three matches are played to five goals.
 * The order is randomized.
 */
public class ThreeMatchesToFiveMode implements GameMode {
	
	private static final Integer [][] mutations = {{0,1,2,3}, {1, 2, 3, 0}, {2, 0, 3, 1}};
	
	private static final Integer MAX_GOALS = 5;

	private static final Integer MAX_MATCHES = 3;
	


	@Override
	public void initializeGame(Game game) {
		game.setMaxMatches(MAX_MATCHES);
		game.setMaxGoals(MAX_GOALS);
	}

	@Override
	public List<Match> createMatches(Game game) {
	    Ranking currentRanking = ofy().load().type(Ranking.class).order("-createdDate").first().now();

		List<String> players = game.getPlayers();
		Collections.shuffle(players); 
		List<Match> matches = new ArrayList<>();
	              Key<Game> gameKey = Key.create(Game.class, game.getId());
		for (int matchIndex = 0; matchIndex < MAX_MATCHES; matchIndex++) {
			Integer [] mutation = mutations[matchIndex];
			Match match = new Match();
			match.setGameKey(gameKey);
			match.setGameId(game.getId());
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

}

package ch.squix.extraleague.rest.games.mode;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.elo.EloUtil;

public class OneMatchToSevenMode implements GameMode {
		
	private static final Integer MAX_GOALS = 7;

	private static final Integer MAX_MATCHES = 1;

	@Override
	public void initializeGame(Game game) {
		game.setMaxMatches(MAX_MATCHES);
		game.setMaxGoals(MAX_GOALS);
	}

	@Override
	public List<Match> createMatches(Game game) {

		Ranking currentRanking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		
		List<String> players = game.getPlayers();
		List<Match> matches = new ArrayList<>();

		Match match = new Match();
		match.setGameId(game.getId());
		match.setTeamA(new String[] {players.get(0), players.get(1)});
		match.setTeamB(new String[] {players.get(2), players.get(3)});
		match.setMaxGoals(MAX_GOALS);
		match.setMaxMatches(MAX_MATCHES);
		match.setPlayers(players);
		match.setTable(game.getTable());
		match.getTags().add(game.getTable());
		match.setMatchIndex(0);
		EloUtil.setEloValuesToMatch(match, currentRanking);
		matches.add(match);

		return matches;
	}


}

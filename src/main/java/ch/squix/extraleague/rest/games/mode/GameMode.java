package ch.squix.extraleague.rest.games.mode;

import java.util.List;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;

public interface GameMode {
	
	void initializeGame(Game game);

	List<Match> createMatches(Game game);

}

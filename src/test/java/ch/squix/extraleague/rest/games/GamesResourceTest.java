package ch.squix.extraleague.rest.games;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.Ranking;

public class GamesResourceTest {
    
        private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
        @Before
        public void setUp() {
            ObjectifyService.register(Game.class);
            ObjectifyService.register(Match.class);
            ObjectifyService.register(BrowserClient.class);
            ObjectifyService.register(Ranking.class);
            
            helper.setUp();
        }
    
        @After
        public void tearDown() {
                helper.tearDown();
        }
	
	@Test
	public void shouldCreateCorrectOrderOfMatches() {
		GamesResource gameRessource = new GamesResource();
		Game game = new Game();
		game.getPlayers().add("a");
		game.getPlayers().add("b");
		game.getPlayers().add("c");
		game.getPlayers().add("d");
		List<Match> matches = gameRessource.createMatches(game);
		testTeams(matches.get(0), "a", "b", "c", "d");
		testTeams(matches.get(1), "b", "c", "d", "a");
		testTeams(matches.get(2), "c", "a", "d", "b");
		testTeams(matches.get(3), "a", "d", "b", "c");
		
		
	}
	
	private void testTeams(Match match, String... players) {
		Assert.assertEquals("Expected different team A in game " + match.getMatchIndex(), new String[]{players[0], players[1]}, match.getTeamA());
		Assert.assertEquals("Expected different team B in game " + match.getMatchIndex(), new String[]{players[2], players[3]}, match.getTeamB());
	}

}

package ch.squix.extraleague.rest.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;

import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.games.mode.FourMatchesToFiveMode;
import ch.squix.extraleague.rest.games.mode.FourMatchesToFiveModeRandom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FourMatchesToFiveRandomTest {
    
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
		Game game = new Game();
		game.setId(1L);
		game.getPlayers().add("a");
		game.getPlayers().add("b");
		game.getPlayers().add("c");
		game.getPlayers().add("d");
		FourMatchesToFiveMode mode = new FourMatchesToFiveModeRandom();
		List<Match> matches = mode.createMatches(game);
		testTeams(matches.get(0), "a", "b", "c", "d");
		testTeams(matches.get(1), "b", "c", "d", "a");
		testTeams(matches.get(2), "c", "a", "d", "b");
		testTeams(matches.get(3), "a", "d", "b", "c");
	}
	
	private void testTeams(Match match, String... players) {
		Assert.assertEquals("Expected different team A in game " + match.getMatchIndex(), new String[]{players[0], players[1]}, match.getTeamA());
		Assert.assertEquals("Expected different team B in game " + match.getMatchIndex(), new String[]{players[2], players[3]}, match.getTeamB());
	}
	
	@Test
	public void shouldOrderPlayerByEloRank() {
		Ranking ranking = new Ranking();
		ranking.getPlayerRankings().add(createPlayerRanking("dei", 1));
		ranking.getPlayerRankings().add(createPlayerRanking("rsp", 2));
		ranking.getPlayerRankings().add(createPlayerRanking("cw", 5));
		FourMatchesToFiveMode mode = new FourMatchesToFiveModeRandom();

		// dei plays twice with cm, rsp twice with cw
		Set<String> team = Sets.newHashSet("dei", "cm");
		List<List<String>> allPairings = new ArrayList<>();
		int counter = 0;

		while (allPairings.size() < 5) {
			counter++;
			assertTrue("Does not find all rotations", counter < 500);
			List<String> players = mode.sortPlayersByRanking(Arrays.asList("rsp", "cm", "dei", "cw"), ranking);

			if (!allPairings.contains(players)) {
				// Opposite players are in the same team
				assertThat(team.contains(players.get(0)), equalTo(team.contains(players.get(3))));
				assertThat(team.contains(players.get(1)), equalTo(team.contains(players.get(2))));
				assertThat(team.contains(players.get(0)), not(equalTo(team.contains(players.get(2)))));
				allPairings.add(players);
			}
		}

	}

	private PlayerRanking createPlayerRanking(String player, Integer rank) {
		PlayerRanking ranking = new PlayerRanking();
		ranking.setPlayer(player);
		ranking.setEloRanking(rank);
		return ranking;
	}

}

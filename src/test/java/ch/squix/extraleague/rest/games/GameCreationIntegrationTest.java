package ch.squix.extraleague.rest.games;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.Method;

import ch.squix.extraleague.model.challenger.ChallengerTeam;
import ch.squix.extraleague.model.challenger.WinnerTeam;
import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.games.mode.GameModeEnum;
import ch.squix.extraleague.rest.matches.MatchDto;
import ch.squix.extraleague.rest.matches.MatchesResource;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

public class GameCreationIntegrationTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
            ObjectifyService.register(Game.class);
            ObjectifyService.register(Match.class);
            ObjectifyService.register(BrowserClient.class);
            ObjectifyService.register(Ranking.class);
            ObjectifyService.register(MeetingPointPlayer.class);
            ObjectifyService.register(PlayerUser.class);
            ObjectifyService.register(ChallengerTeam.class);
            ObjectifyService.register(WinnerTeam.class);
            
	    helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	@Test
	public void shouldCreateGameAndMatches() throws UnsupportedEncodingException {
		GamesResource gameRessource = new GamesResource();
		GameDto gameDto = new GameDto();
		gameDto.getPlayers().add("a");
		gameDto.getPlayers().add("b");
		gameDto.getPlayers().add("c");
		gameDto.getPlayers().add("d");
		gameDto.setGameMode(GameModeEnum.FourMatchesToFive);
		GameDto game = gameRessource.create(gameDto);
		
		MatchesResource matchesRessource = new MatchesResource();
		Request request = new Request(Method.GET, "/test");
		matchesRessource.setRequest(request);
		matchesRessource.getRequestAttributes().put("gameId", "" + game.getId());
		List<MatchDto> matches = matchesRessource.execute();
		Integer matchIndex = 0;
		for (MatchDto match : matches) {
			Assert.assertEquals("Expected different index", matchIndex, match.getMatchIndex());
			matchIndex++;
		}
		Assert.assertEquals("Expected 4 matches", 4, matches.size());
		testOrder(new int[]{1, 2, 3, 0}, 1, matches);
		testOrder(new int[]{2, 0, 3, 1}, 2, matches);
		testOrder(new int[]{0, 3, 1, 2}, 3, matches);
	}
	
	private void testOrder(int[] expectedOrder, int gameToTest, List<MatchDto> matches) {
		String[] order = getOrder(matches.get(0));
		Assert.assertArrayEquals("Expected different order in game " + gameToTest, new String[] {order[expectedOrder[0]], order[expectedOrder[1]], order[expectedOrder[2]], order[expectedOrder[3]]}, getOrder(matches.get(gameToTest)));
	}
	
	private String[] getOrder(MatchDto dto) {
		return new String[] {dto.getTeamA()[0], dto.getTeamA()[1], dto.getTeamB()[0], dto.getTeamB()[1]};
	}

}

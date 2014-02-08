package ch.squix.extraleague.rest.games;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.Method;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
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
		String[] order = getOrder(matches.get(0));
		Assert.assertArrayEquals(new String[] {order[1], order[2], order[3], order[0]}, getOrder(matches.get(1)));
		Assert.assertArrayEquals(new String[] {order[2], order[0], order[3], order[1]}, getOrder(matches.get(2)));
		Assert.assertArrayEquals(new String[] {order[0], order[3], order[1], order[2]}, getOrder(matches.get(3)));
		
	}
	
	private String[] getOrder(MatchDto dto) {
		return new String[] {dto.getTeamA()[0], dto.getTeamA()[1], dto.getTeamB()[0], dto.getTeamB()[1]};
	}

}

package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PartnerOpponentTaskTest {

	@Test
	public void shouldRenderCorrectPartnerResults() {
		List<Match> matchList = new ArrayList<>();
		matchList.add(TestDataUtil.createMatch(5, 4, "dei", "cw", "rfi", "rsp"));
		matchList.add(TestDataUtil.createMatch(4, 5, "cw", "rfi", "rsp", "dei"));
		matchList.add(TestDataUtil.createMatch(4, 5, "rfi", "dei", "rsp", "cw"));
		matchList.add(TestDataUtil.createMatch(5, 4, "dei", "rsp", "cw", "rfi"));
		Matches matches = new Matches();
		matches.setMatches(matchList);
		 
		PartnerOpponentTask task = new PartnerOpponentTask();
		Map<String, PlayerRanking> playerRankingMap = TestDataUtil.createPlayerRankingMap("dei", "cw", "rfi", "rsp");
		task.rankMatches(playerRankingMap, matches);
		PlayerRanking playerDei = playerRankingMap.get("dei");
		Assert.assertEquals("dei", playerDei.getPlayer());
		Assert.assertEquals("rsp", playerDei.getBestPartner());
		Assert.assertEquals(1.0d, playerDei.getBestPartnerRate());
		Assert.assertEquals("rfi", playerDei.getWorstPartner());
		Assert.assertEquals(0d, playerDei.getWorstPartnerRate());
		Assert.assertEquals("rfi", playerDei.getBestOpponent());
		Assert.assertEquals(1.0d, playerDei.getBestOpponentRate());
		Assert.assertEquals("rsp", playerDei.getWorstOpponent());
		Assert.assertEquals(0.5d, playerDei.getWorstOpponentRate());
	}

	
}

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
import ch.squix.extraleague.model.match.Position;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class BestPositionTaskTest {

	@Test
	public void shouldRenderCorrectPartnerResults() {
		List<Match> matchList = new ArrayList<>();
		// Positions 0 and 2 are offense, 1 and 3 are defense
		matchList.add(TestDataUtil.createMatch(5, 4, "dei", "cw", "rfi", "rsp"));
		matchList.add(TestDataUtil.createMatch(4, 5, "cw", "rfi", "rsp", "dei"));
		matchList.add(TestDataUtil.createMatch(4, 5, "rfi", "dei", "rsp", "cw"));
		matchList.add(TestDataUtil.createMatch(5, 4, "dei", "rsp", "cw", "rfi"));
		Matches matches = new Matches();
		matches.setMatches(matchList);
		 
		BestPositionTask task = new BestPositionTask();
		Map<String, PlayerRanking> playerRankingMap = TestDataUtil.createPlayerRankingMap("dei", "cw", "rfi", "rsp");
		task.rankMatches(playerRankingMap, matches);
		PlayerRanking playerDei = playerRankingMap.get("dei");
		Assert.assertEquals(100, Math.round(100 * playerDei.getOffensivePositionRate()));
		Assert.assertEquals(50, Math.round(100 * playerDei.getDefensivePositionRate()));
	}

	
}

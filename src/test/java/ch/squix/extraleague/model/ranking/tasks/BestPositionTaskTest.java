package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Arrays;
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

		List<String> scorers = Arrays.asList("dei", "cw", "dei", "cw", "rfi", "rfi", "rfi", "rsp", "dei"); // 5:4
		matchList.add(TestDataUtil.createMatch(scorers, "dei", "cw", "rfi", "rsp")); // dei->offense, wins
		
		scorers = Arrays.asList("cw", "dei", "cw", "dei", "rsp", "rsp", "rfi", "rfi", "rsp"); // 4:5
		matchList.add(TestDataUtil.createMatch(scorers, "cw", "dei", "rsp", "rfi")); // dei-> defense, looses
		
		scorers = Arrays.asList("rfi", "rfi", "rsp", "rsp", "dei", "dei", "dei", "dei", "dei"); // 4:5
		matchList.add(TestDataUtil.createMatch(scorers, "rfi", "rsp", "dei", "cw")); // dei -> offense, wins
		
		scorers = Arrays.asList("rfi", "rsp", "rsp", "rfi", "cw", "cw", "dei", "dei", "dei"); // 4:5
		matchList.add(TestDataUtil.createMatch(scorers, "rfi", "rsp", "cw", "dei")); // dei -> defense, wins
		
		Matches matches = new Matches();
		matches.setMatches(matchList);
		 
		BestPositionTask task = new BestPositionTask();
		Map<String, PlayerRanking> playerRankingMap = TestDataUtil.createPlayerRankingMap("dei", "cw", "rfi", "rsp");
		task.rankMatches(playerRankingMap, matches);
		PlayerRanking playerDei = playerRankingMap.get("dei");
		Assert.assertEquals("Expected different offensive rate", 100, Math.round(100 * playerDei.getOffensivePositionRate()));
		Assert.assertEquals("Expected different defensive rate", 50, Math.round(100 * playerDei.getDefensivePositionRate()));
	}

	
}

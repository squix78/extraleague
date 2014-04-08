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
import ch.squix.extraleague.model.match.PlayerCombo;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PartnerOpponentTaskTest {
	
	@Test
	public void shouldRenderCorrectPartnerCombos() {
		List<Match> matchList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			matchList.add(TestDataUtil.createMatch(5, 4, "dei", "cw", "rfi", "rsp"));// won
			matchList.add(TestDataUtil.createMatch(4, 5, "cw", "rfi", "rsp", "dei"));// won
			matchList.add(TestDataUtil.createMatch(4, 5, "rfi", "dei", "rsp", "cw"));// lost
			matchList.add(TestDataUtil.createMatch(5, 4, "dei", "rsp", "cw", "rfi"));// won
		}

		Matches matches = new Matches();
		matches.setMatches(matchList);
		PartnerOpponentTask task = new PartnerOpponentTask();
		Map<String, PlayerRanking> playerRankingMap = TestDataUtil.createPlayerRankingMap("dei", "cw", "rfi", "rsp");
		task.rankMatches(playerRankingMap, matches);
		PlayerRanking player = playerRankingMap.get("dei");
		List<PlayerCombo> partners = player.getPartners();
		for (PlayerCombo combo : partners) {
			System.out.println(combo.getCombo() + ", " + combo.getSuccessRate());
		}
		System.out.println("-----");
		List<PlayerCombo> opponents = player.getOpponents();
		for (PlayerCombo combo : opponents) {
			System.out.println(combo.getCombo() + ", " + combo.getSuccessRate());
		}
		checkResult(0, "rsp", 1.0d, partners);
		checkResult(1, "cw", 1.0d, partners);
		checkResult(2, "rfi", 0.0d, partners);
		
		checkResult(0, "rfi", 1.0d, opponents);
		checkResult(1, "cw", 2/3d, opponents);
		checkResult(2, "rsp", 0.5d, opponents);
		
		Assert.assertEquals("rsp", player.getBestPartner());
		Assert.assertEquals(1.0d, player.getBestPartnerRate());
		Assert.assertEquals("rfi", player.getWorstPartner());
		Assert.assertEquals(0d, player.getWorstPartnerRate());
		
		Assert.assertEquals("rfi", player.getBestOpponent());
		Assert.assertEquals(1.0d, player.getBestOpponentRate());
		Assert.assertEquals("rsp", player.getWorstOpponent());
		Assert.assertEquals(0.5d, player.getWorstOpponentRate());
		
	}

	private void checkResult(int index, String player, Double successRate, List<PlayerCombo> combos) {
		PlayerCombo combo = combos.get(index);
		Assert.assertEquals(player, combo.getCombo());
		Assert.assertEquals(successRate, combo.getSuccessRate());
	}

	
}

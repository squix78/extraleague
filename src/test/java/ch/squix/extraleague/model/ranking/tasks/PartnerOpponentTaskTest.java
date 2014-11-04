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
import ch.squix.extraleague.model.match.PlayerCombo;
import ch.squix.extraleague.model.ranking.PlayerRanking;

public class PartnerOpponentTaskTest {
	
	@Test
	public void shouldRenderCorrectPartnerCombos() {
		List<Match> matchList = new ArrayList<>();
		List<String> scorers = null;
		for (int i = 0; i < 3; i++) {

			// dei-cw : rfi-rsp, 5:4
			scorers = Arrays.asList("dei", "cw", "dei", "cw", "rfi", "rfi", "rfi", "rsp", "dei");
			matchList.add(TestDataUtil.createMatch(scorers, "dei", "cw", "rfi", "rsp"));
			
			// cw-rf : rsp-dei: 4:5, dei keeper, gets 3 from cw, 1 from rfi
			scorers = Arrays.asList("cw", "rfi", "cw", "cw", "rsp", "rsp", "dei", "dei", "dei");
			matchList.add(TestDataUtil.createMatch(scorers, "cw", "rfi", "rsp", "dei"));
			
			// rfi-dei: rsp-cw: 4:5, dei keeper, gets 3 from rsp and 2 from cw
			scorers = Arrays.asList("rfi", "dei", "dei", "dei", "rsp", "rsp", "rsp", "cw", "cw");
			matchList.add(TestDataUtil.createMatch(scorers, "rfi", "dei", "rsp", "cw"));
			
			// dei-rsp : cw-rfi: 5:4
			scorers = Arrays.asList("dei", "dei", "rsp", "rsp", "cw", "cw", "cw", "cw", "dei");
			matchList.add(TestDataUtil.createMatch(scorers, "dei", "rsp", "cw", "rfi"));
		}
		// dei keeper:
		// 2 matches against cw, receives 5 -> goal rate: 2.5
		// 1 match against rfi, receives 1 goals-> goal rate: 1
		// 1 match against rsp, receives 3 goals-> goal rate: 3

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
		checkSuccessRate(0, "rsp", 1.0d, partners);
		checkSuccessRate(1, "cw", 1.0d, partners);
		checkSuccessRate(2, "rfi", 0.0d, partners);
		
		checkKeeperGoalRate(0, "rfi", 1.0d, opponents);
		checkKeeperGoalRate(1, "cw", 2.5d, opponents);
		checkKeeperGoalRate(2, "rsp", 3.0d, opponents);
		
		checkSuccessRate(0, "rfi", 1.0d, opponents);
		checkSuccessRate(1, "cw", 2/3d, opponents);
		checkSuccessRate(2, "rsp", 0.5d, opponents);
		
		Assert.assertEquals("rsp", player.getBestPartner());
		Assert.assertEquals(1.0d, player.getBestPartnerRate());
		Assert.assertEquals("rfi", player.getWorstPartner());
		Assert.assertEquals(0d, player.getWorstPartnerRate());
		
		Assert.assertEquals("rfi", player.getBestOpponent());
		Assert.assertEquals(1.0d, player.getBestOpponentRate());
		Assert.assertEquals("rsp", player.getWorstOpponent());
		Assert.assertEquals(0.5d, player.getWorstOpponentRate());
		
	}

	private void checkSuccessRate(int index, String player, Double successRate, List<PlayerCombo> combos) {
		PlayerCombo combo = combos.get(index);
		Assert.assertEquals(player, combo.getCombo());
		Assert.assertEquals(successRate, combo.getSuccessRate());
	}
	
	private void checkKeeperGoalRate(int index, String player, Double keeperGoalRate, List<PlayerCombo> combos) {
		PlayerCombo combo = combos.get(index);
		Assert.assertEquals(player, combo.getCombo());
		Assert.assertEquals(keeperGoalRate, combo.getKeeperGoalRate());
	}

	
}

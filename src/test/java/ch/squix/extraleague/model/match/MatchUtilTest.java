package ch.squix.extraleague.model.match;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class MatchUtilTest {

    @Test
    public void shouldReturnMatchPlayerResults() {
        Match match = new Match();
        match.setTeamA(new String[]{"A", "B"});
        match.setTeamB(new String[]{"C", "D"});
        match.setTeamAScore(5);
        match.setTeamBScore(3);
        match.getScorers().addAll(Arrays.asList("A", "A", "A", "C", "C", "D", "A", "A"));
        List<PlayerMatchResult> results = MatchUtil.getPlayerMatchResults(match);
        
        Assert.assertEquals(4, results.size());
        runCheck(results.get(0), "A", "B", new String[] {"C", "D"}, 5, 3, true, 5);
        runCheck(results.get(1), "B", "A", new String[] {"C", "D"}, 5, 3, true, 0);
        runCheck(results.get(2), "C", "D", new String[] {"A", "B"}, 3, 5, false, 2);
        runCheck(results.get(3), "D", "C", new String[] {"A", "B"}, 3, 5, false, 1);
       
    }

    private void runCheck(PlayerMatchResult result, String player, String partner, String[] opponents, Integer goalsMade, Integer goalsGot, Boolean hasWon, Integer playerGoals) {
        Assert.assertEquals(player, result.getPlayer());
        Assert.assertEquals(partner, result.getPartner());
        Assert.assertArrayEquals(opponents, result.getOpponents());
        Assert.assertEquals(goalsMade, result.getGoalsMade());
        Assert.assertEquals(goalsGot, result.getGoalsGot());
        Assert.assertEquals(hasWon, result.hasWon());
        Assert.assertEquals(playerGoals, result.getPlayerGoals());
    }
    
}

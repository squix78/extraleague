/*
 * Copyright (C) 2014 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package ch.squix.extraleague.model.match;

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
        List<PlayerMatchResult> results = MatchUtil.getPlayerMatchResults(match);
        
        Assert.assertEquals(4, results.size());
        runCheck(results.get(0), "A", 5, 3, true);
        runCheck(results.get(1), "B", 5, 3, true);
        runCheck(results.get(2), "C", 3, 5, false);
        runCheck(results.get(3), "D", 3, 5, false);
    }

    private void runCheck(PlayerMatchResult result, String player, Integer goalsMade, Integer goalsGot, Boolean hasWon) {
        Assert.assertEquals(player, result.getPlayer());
        Assert.assertEquals(goalsMade, result.getGoalsMade());
        Assert.assertEquals(goalsGot, result.getGoalsGot());
        Assert.assertEquals(hasWon, result.hasWon());
    }
    
}

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

import java.util.ArrayList;
import java.util.List;


public class MatchUtil {
    
    public static List<PlayerMatchResult> getPlayerMatchResults(Match match) {
        List<PlayerMatchResult> results = new ArrayList<>();
        boolean hasTeamAWon = match.getTeamAScore() > match.getTeamBScore();
        int index = 0;
        for (String player : match.getTeamA()) {
            PlayerMatchResult result = new PlayerMatchResult();
            result.setPlayer(player);
            result.setPartner(match.getTeamA()[(index + 1) % 2]);
            result.setOpponents(match.getTeamB());
            result.setGoalsMade(match.getTeamAScore());
            result.setGoalsGot(match.getTeamBScore());
            result.setHasWon(hasTeamAWon);
            results.add(result);
            index++;
        }
        index = 0;
        for (String player : match.getTeamB()) {
            PlayerMatchResult result = new PlayerMatchResult();
            result.setPlayer(player);
            result.setPartner(match.getTeamB()[(index + 1) % 2]);
            result.setOpponents(match.getTeamA());
            result.setGoalsMade(match.getTeamBScore());
            result.setGoalsGot(match.getTeamAScore());
            result.setHasWon(!hasTeamAWon);
            results.add(result);
            index++;
        }
        return results;
    }

}

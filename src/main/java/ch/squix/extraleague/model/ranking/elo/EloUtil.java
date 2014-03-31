/*
 * Copyright (C) 2014 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package ch.squix.extraleague.model.ranking.elo;

import java.util.Map;

import ch.squix.extraleague.model.ranking.PlayerRanking;


public class EloUtil {
    
    private final static Double K_FACTOR = 16d;
    public final static Integer INITIAL_RATING = 1000;


    public static Integer getEloValue(Map<String, PlayerRanking> playerRankingMap, String[] team) {
            Integer eloValuePlayer0 = playerRankingMap.get(team[0]).getEloValue();
            Integer eloValuePlayer1 = playerRankingMap.get(team[1]).getEloValue();
            return (int) Math.round((eloValuePlayer0 + eloValuePlayer1) / 2d);
    }

    public static Double getExpectedOutcome(Integer rating, Integer opponentRating) {
            return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - rating) / 400.0)));
    }
    
    public static Integer calculateDelta(Double score, Double expectedScore) {
        return (int) (K_FACTOR * (score - expectedScore));
    }

}

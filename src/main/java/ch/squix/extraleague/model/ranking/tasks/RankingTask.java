/*
 * Copyright (C) 2014 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package ch.squix.extraleague.model.ranking.tasks;

import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public interface RankingTask {
    
    /**
     * Ranks matches under specific aspects.
     * @param matches
     */
    void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches);

}

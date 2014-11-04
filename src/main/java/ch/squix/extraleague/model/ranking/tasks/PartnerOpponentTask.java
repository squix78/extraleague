package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerCombo;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.match.Position;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class PartnerOpponentTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        Map<String, Map<String, PlayerCombo>> partnerMap = new HashMap<>();
        Map<String, Map<String, PlayerCombo>> opponentMap = new HashMap<>();
        for (Match match : matches.getMatches()) {
            List<PlayerMatchResult> playerMatches = MatchUtil.getPlayerMatchResults(match);
            for (PlayerMatchResult playerMatch : playerMatches) {
                updatePlayerCombo(partnerMap, opponentMap, playerMatch);
            }
        }
        calculatePartnerAndOpponents(playerRankingMap, partnerMap, opponentMap);
    }
    
    private void calculatePartnerAndOpponents(Map<String, PlayerRanking> playerRankingMap,
            Map<String, Map<String, PlayerCombo>> partnerMap, Map<String, Map<String, PlayerCombo>> opponentMap) {
    PlayerComboComparator comparator = new PlayerComboComparator();
    for (String player : playerRankingMap.keySet()) {
            PlayerRanking ranking = playerRankingMap.get(player);
            Map<String, PlayerCombo> partnerComboMap = partnerMap.get(player);


            List<PlayerCombo> partners = filterLowFrequenceCombos(partnerComboMap);
            if (partners.size() > 0) {
            	Collections.sort(partners, comparator);
	            PlayerCombo bestPartner = partners.get(0);
				ranking.setBestPartner(bestPartner.getCombo());
	            ranking.setBestPartnerRate(bestPartner.getSuccessRate());
	            PlayerCombo worstPartner = partners.get(partners.size() - 1);
				ranking.setWorstPartner(worstPartner.getCombo());
	            ranking.setWorstPartnerRate(worstPartner.getSuccessRate());
	            ranking.setPartners(partners);
            }

            Map<String, PlayerCombo> opponentComboMap = opponentMap.get(player);

            List<PlayerCombo> opponents = filterLowFrequenceCombos(opponentComboMap);
            if (opponents.size() > 0) {
	            Collections.sort(opponents, comparator);
	
	            PlayerCombo bestOpponent = opponents.get(0);
				ranking.setBestOpponent(bestOpponent.getCombo());
	            ranking.setBestOpponentRate(bestOpponent.getSuccessRate());
	            PlayerCombo worstOpponent = opponents.get(opponents.size() - 1);
				ranking.setWorstOpponent(worstOpponent.getCombo());
	            ranking.setWorstOpponentRate(worstOpponent.getSuccessRate());
	            ranking.setOpponents(opponents);
            }
            
    }
}

    private List<PlayerCombo> filterLowFrequenceCombos(Map<String, PlayerCombo> partnerComboMap) {
		List<PlayerCombo> players = new ArrayList<>();
		for (PlayerCombo combo : partnerComboMap.values()) {
			if (combo.getTotalGames() > 2) {
				players.add(combo);
			}
		}
		return players;
	}

	private void updatePlayerCombo(Map<String, Map<String, PlayerCombo>> partnerMap,
            Map<String, Map<String, PlayerCombo>> opponentMap,
            PlayerMatchResult playerMatch) {
        PlayerCombo partner = getPlayerCombo(partnerMap, playerMatch.getPlayer(),
                playerMatch.getPartner());
        if (playerMatch.isWon()) {
            partner.increaseGamesWon();
        } else {
            partner.increaseGamesLost();
        }
        for (String opponentName : playerMatch.getOpponents()) {
            PlayerCombo opponent = getPlayerCombo(opponentMap, playerMatch.getPlayer(),
                    opponentName);
            if (playerMatch.isWon()) {
            	opponent.increaseGamesWon();
            } else {
            	opponent.increaseGamesLost();
            }
            if (playerMatch.getPosition() == Position.Defensive) {
            	Integer goalsReceived = Collections.frequency(playerMatch.getScorers(), opponentName);
            	opponent.setGoalsReceived(opponent.getGoalsReceived() + goalsReceived);
            	opponent.setPlayedAsKeeper(opponent.getPlayedAsKeeper() + 1);
            }
        }
    }

    private PlayerCombo getPlayerCombo(Map<String, Map<String, PlayerCombo>> playerComboMap,
            String player,
            String comboName) {
        Map<String, PlayerCombo> map = playerComboMap.get(player);
        if (map == null) {
            map = new HashMap<>();
            playerComboMap.put(player, map);
        }
        PlayerCombo combo = map.get(comboName);
        if (combo == null) {
            combo = new PlayerCombo();
            combo.setPlayer(player);
            combo.setCombo(comboName);
            map.put(comboName, combo);
        }
        return combo;
    }
    
    
    private class PlayerComboComparator implements Comparator<PlayerCombo> {

            @Override
            public int compare(PlayerCombo o1, PlayerCombo o2) {
                    int result = o2.getSuccessRate().compareTo(o1.getSuccessRate());
                    if (result == 0) {
                            return o2.getTotalGames().compareTo(o1.getTotalGames());
                    }
                    return result;
            }
            
    }

}

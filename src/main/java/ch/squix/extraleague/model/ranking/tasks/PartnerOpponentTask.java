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
    for (String player : playerRankingMap.keySet()) {
            PlayerRanking ranking = playerRankingMap.get(player);
            Map<String, PlayerCombo> partnerComboMap = partnerMap.get(player);

            PlayerComboComparator comparator = new PlayerComboComparator();

            List<PlayerCombo> partners = new ArrayList<>(partnerComboMap.values());
            Collections.sort(partners, comparator);

            ranking.setBestPartner(partners.get(0).getCombo());
            ranking.setBestPartnerRate(partners.get(0).getSuccessRate());
            ranking.setWorstPartner(partners.get(partners.size() - 1).getCombo());
            ranking.setWorstPartnerRate(partners.get(partners.size() - 1).getSuccessRate());


            Map<String, PlayerCombo> opponentComboMap = opponentMap.get(player);

            List<PlayerCombo> opponents = new ArrayList<>(opponentComboMap.values());
            Collections.sort(opponents, comparator);

            ranking.setBestOpponent(opponents.get(opponents.size() - 1).getCombo());
            ranking.setBestOpponentRate(1 - opponents.get(opponents.size() - 1).getSuccessRate());
            ranking.setWorstOpponent(opponents.get(0).getCombo());
            ranking.setWorstOpponentRate(1 - opponents.get(0).getSuccessRate());
            
    }
}

    private void updatePlayerCombo(Map<String, Map<String, PlayerCombo>> partnerMap,
            Map<String, Map<String, PlayerCombo>> opponentMap,
            PlayerMatchResult playerMatch) {
        PlayerCombo partner = getPlayerCombo(partnerMap, playerMatch.getPlayer(),
                playerMatch.getPartner());
        if (playerMatch.hasWon()) {
            partner.increaseGamesWon();
        } else {
            partner.increaseGamesLost();
        }
        for (String opponentName : playerMatch.getOpponents()) {
            PlayerCombo opponent = getPlayerCombo(opponentMap, playerMatch.getPlayer(),
                    opponentName);
            if (playerMatch.hasWon()) {
                opponent.increaseGamesLost();
            } else {
                opponent.increaseGamesWon();
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

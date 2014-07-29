package ch.squix.extraleague.model.ranking.tasks;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;


public class RankingIndexTask implements RankingTask {
	
	private static final Logger log = Logger.getLogger(RankingIndexTask.class.getName());

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        setSuccessRateRankingIndex(playerRankingMap);
        setEloRankingIndex(playerRankingMap);
        setTrueSkillsRankingIndex(playerRankingMap);
		calculateBadges(playerRankingMap);
    }

    private void setSuccessRateRankingIndex(Map<String, PlayerRanking> playerRankingMap) {
        List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
        Collections.sort(rankings, new Comparator<PlayerRanking>() {

            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                int result = o2.getSuccessRate().compareTo(o1.getSuccessRate());
                if (result == 0) {
                    return o2.getGoalRate().compareTo(o1.getGoalRate());
                }
                return result;
            }

        });
        int index = 1;
        for (PlayerRanking ranking : rankings) {
            ranking.setRanking(index);
            index++;
        }
    }
    
    private void setEloRankingIndex(Map<String, PlayerRanking> playerRankingMap) {
        List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
        Collections.sort(rankings, new Comparator<PlayerRanking>() {

            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                int result = o2.getEloValue().compareTo(o1.getEloValue());
                if (result == 0) {
                    return o2.getGoalRate().compareTo(o1.getGoalRate());
                }
                return result;
            }

        });
        int index = 1;
        for (PlayerRanking ranking : rankings) {
            ranking.setEloRanking(index);
            index++;
        }
    }
    
    private void setTrueSkillsRankingIndex(Map<String, PlayerRanking> playerRankingMap) {
        List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
        Collections.sort(rankings, new Comparator<PlayerRanking>() {

            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                int result = o2.getTrueSkillRating().compareTo(o1.getTrueSkillRating());
                if (result == 0) {
                    return o2.getGoalRate().compareTo(o1.getGoalRate());
                }
                return result;
            }

        });
        int index = 1;
        for (PlayerRanking ranking : rankings) {
            ranking.setTrueSkillRanking(index);
            index++;
        }
    }

	private void calculateBadges(Map<String, PlayerRanking> playerRankingMap) {
        Collection<PlayerRanking> rankings = playerRankingMap.values();
        for (PlayerRanking ranking : rankings) {
			if (ranking.getEloRanking() == 1) {
				ranking.getBadges().add(BadgeEnum.King.name());
			}
			if (ranking.getEloRanking() == 2) {
				ranking.getBadges().add(BadgeEnum.Queen.name());
			}
			if (ranking.getEloRanking() == rankings.size()) {
				ranking.getBadges().add(BadgeEnum.Pawn.name());
			}
		}
	}

}

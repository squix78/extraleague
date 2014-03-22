package ch.squix.extraleague.model.ranking.tasks;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by rweiss on 21.03.14.
 */
public class DynamicRankingIndexTask implements RankingTask {

    private static final int POINTS_PER_GAME = 100;

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        SortedSet<Match> matchesSortedByDate = getMathesSortedByDate(matches);
        for (Match match : matchesSortedByDate) {
            float weightTeamA = getTeamWeight(match.getTeamA(), playerRankingMap);
            float weightTeamB = getTeamWeight(match.getTeamB(), playerRankingMap);

            int rankingPoints = POINTS_PER_GAME;
            if (MatchUtil.hasTeamAWon(match)) {
                if (weightTeamA != 0) {
                    rankingPoints = (int) ((weightTeamB / weightTeamA) * POINTS_PER_GAME);
                }
                addRankingPoints(match.getTeamA(), rankingPoints, playerRankingMap);
            } else {
                if (weightTeamB != 0) {
                    rankingPoints = (int) ((weightTeamA / weightTeamB) * POINTS_PER_GAME);
                }
                addRankingPoints(match.getTeamB(), rankingPoints, playerRankingMap);
            }
        }

        SortedSet<PlayerRanking> sortedPlayerRanking = getSortedPlayerRanking(playerRankingMap.values());
        int i = 1;
        for (PlayerRanking playerRanking : sortedPlayerRanking) {
            playerRanking.setDynamicRanking(i++);
        }
    }

    private SortedSet<PlayerRanking> getSortedPlayerRanking(Collection<PlayerRanking> playerRankings) {
        TreeSet<PlayerRanking> sortedPlayerRankings = new TreeSet<>(new Comparator<PlayerRanking>() {
            @Override
            public int compare(PlayerRanking o1, PlayerRanking o2) {
                return o2.getRankingPoints().compareTo(o1.getRankingPoints());
            }
        });
        sortedPlayerRankings.addAll(playerRankings);
        return sortedPlayerRankings;
    }

    private float getTeamWeight(String[] teamMembers, Map<String, PlayerRanking> playerRankingMap) {
        float weight = 0;
        for (String teamMember : teamMembers) {
            PlayerRanking playerRanking = playerRankingMap.get(teamMember);
            if (playerRanking.getTotalGames() > 0) {
                weight += playerRanking.getRankingPoints() / playerRanking.getTotalGames();
            }
        }
        return weight / teamMembers.length;
    }

    private void addRankingPoints(String[] teamMembers, int rankingPoints, Map<String, PlayerRanking> playerRankingMap) {
        for (String teamMember : teamMembers) {
            PlayerRanking playerRanking = playerRankingMap.get(teamMember);
            playerRanking.addRankingPoints(rankingPoints);
        }
    }

    private SortedSet<Match> getMathesSortedByDate(Matches matches) {
        TreeSet<Match> sortedMatches = new TreeSet<>(new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                int i = o1.getEndDate().compareTo(o2.getEndDate());
                return i;
            }
        });
        sortedMatches.addAll(matches.getMatches());
        return sortedMatches;

    }
}

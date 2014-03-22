package ch.squix.extraleague.model.ranking.tasks;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by rweiss on 21.03.14.
 */
public class DynamicRankingIndexTask implements RankingTask {

    private static final int POINTS_PER_GAME = 1000;

    private Map<String, PointsAccumulation> playersPointsAccumulation;

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {

        preparePlayerPointsAccumulation(playerRankingMap);

        SortedSet<Match> matchesSortedByDate = getMathesSortedByDate(matches);
        for (Match match : matchesSortedByDate) {
            float weightTeamA = getTeamWeight(match.getTeamA(), playerRankingMap);
            float weightTeamB = getTeamWeight(match.getTeamB(), playerRankingMap);


            int pointsForTeamA = 0;
            int PointsForTeamB = 0;

            if (MatchUtil.hasTeamAWon(match)) {
                if (weightTeamA != 0) {
                    pointsForTeamA = (int) ((weightTeamB / weightTeamA) * POINTS_PER_GAME);
                }else{
                    pointsForTeamA = POINTS_PER_GAME;
                }
            } else {
                if (weightTeamB != 0) {
                    PointsForTeamB = (int) ((weightTeamA / weightTeamB) * POINTS_PER_GAME);
                }else{
                    PointsForTeamB = POINTS_PER_GAME;
                }
            }
            addRankingPoints(match.getTeamA(), pointsForTeamA);
            addRankingPoints(match.getTeamB(), PointsForTeamB);
        }

        for (Map.Entry<String, PlayerRanking> playerRankingEntry : playerRankingMap.entrySet()) {
            PointsAccumulation pointsAccumulation = playersPointsAccumulation.get(playerRankingEntry.getKey());
            playerRankingEntry.getValue().setRankingPoints(pointsAccumulation.getAveragePoints());
        }
    }

    private void preparePlayerPointsAccumulation(Map<String, PlayerRanking> playerRankingMap) {
        playersPointsAccumulation = new HashMap<>();
        for (PlayerRanking playerRanking : playerRankingMap.values()) {
            String playerName = playerRanking.getPlayer();
            if (!playersPointsAccumulation.containsKey(playerName)) {
                playersPointsAccumulation.put(playerName, new PointsAccumulation());
            }
        }
    }



    private float getTeamWeight(String[] teamMembers, Map<String, PlayerRanking> playerRankingMap) {
        float weight = 0;
        for (String teamMember : teamMembers) {
            PlayerRanking playerRanking = playerRankingMap.get(teamMember);
           weight += getCurrentAveragePoints(teamMember);
        }
        return weight / teamMembers.length;
    }

    private int getCurrentAveragePoints(String teamMember) {
        PointsAccumulation pointsAccumulation = playersPointsAccumulation.get(teamMember);
        return pointsAccumulation.getAveragePoints();
    }

    private void addRankingPoints(String[] teamMembers, int rankingPoints) {
        for (String teamMember : teamMembers) {
            PointsAccumulation pointsAccumulation = playersPointsAccumulation.get(teamMember);
            pointsAccumulation.addPointsOfMatch(rankingPoints);
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

    static class PointsAccumulation {
        private int numberOfMatches = 0;
        private int accumulatedPoints = 0;

        void addPointsOfMatch(int pointsToAdd) {
            numberOfMatches++;
            accumulatedPoints += pointsToAdd;
        }

        int getAveragePoints() {
            return numberOfMatches == 0 ? 0 : accumulatedPoints / numberOfMatches;
        }
    }
}

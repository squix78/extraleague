package ch.squix.extraleague.model.ranking.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.match.Position;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class BestPositionTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        for (String player : matches.getPlayers()) {
                PlayerRanking ranking = playerRankingMap.get(player);
                List<Match> matchesByPlayer = matches.getMatchesByPlayer(player);
                Map<Position, Integer> positionMap = new HashMap<>();
                positionMap.put(Position.Offensive, 0);
                positionMap.put(Position.Defensive, 0);

                for (Match match : matchesByPlayer) {
                        PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
                        if (playerMatch.hasWon()) {
                                Position position = playerMatch.getPosition();
                                Integer positionCount = positionMap.get(position) + 1;
                                positionMap.put(position, positionCount);
                        }

                }

                Integer offensiveCount = positionMap.get(Position.Offensive);
                Integer defensiveCount = positionMap.get(Position.Defensive);
                ranking.setOffensivePositionRate(1.0 * offensiveCount / (matchesByPlayer.size()));
                ranking.setDefensivePositionRate(1.0 * defensiveCount / (matchesByPlayer.size()));

        }
    }

}

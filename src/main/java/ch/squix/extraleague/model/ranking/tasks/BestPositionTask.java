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
                Map<Position, Integer> gamesWonOnPosition = new HashMap<>();
                gamesWonOnPosition.put(Position.Offensive, 0);
                gamesWonOnPosition.put(Position.Defensive, 0);
                
                Map<Position, Integer> gamesPlayedOnPosition = new HashMap<>();
                gamesPlayedOnPosition.put(Position.Offensive, 0);
                gamesPlayedOnPosition.put(Position.Defensive, 0);

                for (Match match : matchesByPlayer) {
                        PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
                        Position position = playerMatch.getPosition();
                        if (playerMatch.hasWon()) {
                                gamesWonOnPosition.put(position, gamesWonOnPosition.get(position) + 1);
                        }
                        gamesPlayedOnPosition.put(position, gamesPlayedOnPosition.get(position) + 1);

                }

                ranking.setOffensivePositionRate(1.0 * gamesWonOnPosition.get(Position.Offensive) / gamesPlayedOnPosition.get(Position.Offensive));
                ranking.setDefensivePositionRate(1.0 * gamesWonOnPosition.get(Position.Defensive) / gamesPlayedOnPosition.get(Position.Defensive));

        }
    }

}

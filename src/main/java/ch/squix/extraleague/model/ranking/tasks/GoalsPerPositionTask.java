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


public class GoalsPerPositionTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
        for (String player : matches.getPlayers()) {
                PlayerRanking ranking = playerRankingMap.get(player);
                List<Match> matchesByPlayer = matches.getMatchesByPlayer(player);
                Map<Position, Integer> goalsPerPosition = new HashMap<>();
                goalsPerPosition.put(Position.Offensive, 0);
                goalsPerPosition.put(Position.Defensive, 0);
          

                for (Match match : matchesByPlayer) {
                        PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
                        Position position = playerMatch.getPosition();
                        Integer goals = playerMatch.getGoalsMade();
                        goalsPerPosition.put(position, goalsPerPosition.get(position) + goals);
                }
                Integer totalGoals = goalsPerPosition.get(Position.Offensive) + goalsPerPosition.get(Position.Defensive);
                Integer offensiveGoals = goalsPerPosition.get(Position.Offensive);
                if (totalGoals != 0) {
                	ranking.setOffensiveGoalsRate(1.0 * offensiveGoals / totalGoals);
                }


        }
    }

}

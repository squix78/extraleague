package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.rest.statistics.DataTuple;


public class ScoreHistogramTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	String [] sortOrder = {"0:5", "1:5", "2:5", "3:5", "4:5", "5:4", "5:3", "5:2", "5:1", "5:0"};
        for (String player : matches.getPlayers()) {
        	PlayerRanking ranking = playerRankingMap.get(player);
        	Map<String, Integer> frequencyMap = new TreeMap<>();
        	Integer resultCounter = 0;
        	for (Match match : matches.getMatchesByPlayer(player)) {
        		PlayerMatchResult playerMatchResult = MatchUtil.getPlayerMatchResult(match, player);
        		String result = playerMatchResult.getMatchResultAsPlayersView();
        		Integer frequency = frequencyMap.get(result);
        		if (frequency == null) {
        			frequency = 0;
        		}
        		frequency++;
        		resultCounter++;
        		frequencyMap.put(result, frequency);
        	}
        	List<DataTuple<Integer, Double>> scoreHistogram = new ArrayList<>();
        	Integer index = 0;
        	for (String result : sortOrder) {
        		Integer count = frequencyMap.get(result);
        		if (count == null) {
        			count = 0;
        		}
        		Double percentage = 1d * count / resultCounter;
        		scoreHistogram.add(new DataTuple<Integer, Double>(index, percentage, result));
        		index++;
        	}
        	ranking.setScoreHistogram(scoreHistogram);

        }
    }

}

package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.MatchUtil;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.match.PlayerMatchResult;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class TightMatchesTask implements RankingTask {
	
	private static final Logger log = Logger.getLogger(TightMatchesTask.class.getName());

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	List<PlayerRanking> playerRankings = new ArrayList<>();
        for (String player : matches.getPlayers()) {
        	PlayerRanking ranking = playerRankingMap.get(player);
        	int numberOfTightlyLostMatches = 0;
        	int numberOfTightlyWonMatches = 0;
        	int numberOfMatches = 0;
        	for (Match match : matches.getMatchesByPlayer(player)) {
        		PlayerMatchResult playerMatch = MatchUtil.getPlayerMatchResult(match, player);
        		Integer goalsGot = playerMatch.getGoalsGot();
				Integer goalsMade = playerMatch.getGoalsMade();
				if (goalsGot == 5 && goalsMade == 4) {
        			numberOfTightlyLostMatches++;
        		}
        		if (goalsMade == 5 && goalsGot == 4) {
        			numberOfTightlyWonMatches++;
        		}
        		numberOfMatches++;
        	}
        	if (numberOfMatches >= 8) {
	        	playerRankings.add(ranking);
	        	Double tightlyLostRate = 1d * numberOfTightlyLostMatches / numberOfMatches;
	        	ranking.setTightlyLostRate(tightlyLostRate);
	        	Double tightlyWonRate = 1d * numberOfTightlyWonMatches / numberOfMatches;
	        	ranking.setTightlyWonRate(tightlyWonRate);
        	}

        }
        Collections.sort(playerRankings, new TightlyLostComparator());
        PlayerRanking maxTightlyLostPlayer = playerRankings.get(0);
        maxTightlyLostPlayer.getBadges().add(BadgeEnum.BundleOfNerves.name());
        log.info("Bundle of nerves: " + maxTightlyLostPlayer.getPlayer() + " with " + maxTightlyLostPlayer.getTightlyLostRate());

        Collections.sort(playerRankings, new TightlyWonComparator());
        PlayerRanking maxTightlyWonPlayer = playerRankings.get(0);
        maxTightlyWonPlayer.getBadges().add(BadgeEnum.SteelRopeNerves.name());
        log.info("Steel Rope Nerves: " + maxTightlyWonPlayer.getPlayer() + " with " + maxTightlyWonPlayer.getTightlyWonRate());
        
    }
    
    private class TightlyLostComparator implements Comparator<PlayerRanking> {

		@Override
		public int compare(PlayerRanking o1, PlayerRanking o2) {
			return o2.getTightlyLostRate().compareTo(o1.getTightlyLostRate());
		}
    	
    }
    
    private class TightlyWonComparator implements Comparator<PlayerRanking> {
    	
    	@Override
    	public int compare(PlayerRanking o1, PlayerRanking o2) {
    		return o2.getTightlyWonRate().compareTo(o1.getTightlyWonRate());
    	}
    	
    }

}

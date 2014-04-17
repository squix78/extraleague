package ch.squix.extraleague.model.ranking.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;


public class PartnerCountTask implements RankingTask {

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	Integer maxPlayedWithCount = 0;
    	Integer minPlayedWithCount = Integer.MAX_VALUE;
    	for (String player : playerRankingMap.keySet()) {
    		PlayerRanking ranking = playerRankingMap.get(player);
    		Set<String> playedWith = new HashSet<>();
    		Set<String> neverPlayedWith = new HashSet<>();
    		neverPlayedWith.addAll(matches.getPlayers());
    		for (Match match : matches.getMatchesByPlayer(player)) {
    			playedWith.addAll(match.getPlayers());
    			neverPlayedWith.removeAll(match.getPlayers());
    		}
    		playedWith.remove(player);
    		ranking.setPlayedWith(playedWith);
    		ranking.setNeverPlayedWith(neverPlayedWith);
    		maxPlayedWithCount = Math.max(playedWith.size(), maxPlayedWithCount);
    		minPlayedWithCount = Math.min(playedWith.size(), minPlayedWithCount);

    	}
    	List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
    	Collections.sort(rankings, new Comparator<PlayerRanking>() {

			@Override
			public int compare(PlayerRanking o1, PlayerRanking o2) {
				Integer o1PlayedWith = o1.getPlayedWith().size();
				Integer o2PlayedWith = o2.getPlayedWith().size();
				return o1PlayedWith.compareTo(o2PlayedWith);
			}
		});
    	for (PlayerRanking ranking : rankings) {
    		if (ranking.getPlayedWith().size() == maxPlayedWithCount) {
    			ranking.getBadges().add(BadgeEnum.GrayEminence.name());
    		}
    		if (ranking.getPlayedWith().size() == minPlayedWithCount) {
    			ranking.getBadges().add(BadgeEnum.LoneWolf.name());
    		}
    	}
    	
    	
	}

}

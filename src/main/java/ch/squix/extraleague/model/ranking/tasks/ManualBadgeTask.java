package ch.squix.extraleague.model.ranking.tasks;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;


public class ManualBadgeTask implements RankingTask {
	
	private static final Logger log = Logger.getLogger(ManualBadgeTask.class.getName());

    @Override
    public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
    	try {
    		Properties badges = new Properties();
			badges.load(this.getClass().getClassLoader().getResourceAsStream("ManualBadges.properties"));
			for (Entry<Object, Object> entry : badges.entrySet()) {
				String player = String.valueOf(entry.getKey());
				PlayerRanking playerRanking = playerRankingMap.get(player);
				playerRanking.getBadges().add(String.valueOf(entry.getValue()));
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "Could not read properties for manual badges", e);
		}
    }

}

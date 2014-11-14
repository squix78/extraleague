package ch.squix.extraleague.model.ranking.tasks;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Map;

import org.joda.time.LocalDate;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.badge.BadgeEnum;

public class EmperorTask implements RankingTask {


	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		LocalDate today = new LocalDate();                                   
		LocalDate weekStart = today.dayOfWeek().withMinimumValue();

		Ranking weekStartRanking = ofy().load().type(Ranking.class).order("createdDate").filter("createdDate > ", weekStart.toDate()).limit(1).first().now();
		if (weekStartRanking != null) {
			for (PlayerRanking weekStartPlayerRanking : weekStartRanking.getPlayerRankings()) {
				if (weekStartPlayerRanking.getBadges().contains(BadgeEnum.King.name())) {
					PlayerRanking playerRanking = playerRankingMap.get(weekStartPlayerRanking.getPlayer());
					if (playerRanking != null) {
						playerRanking.getBadges().add(BadgeEnum.Emperor.name());
					}
				}
				

			}
		}
	}

}

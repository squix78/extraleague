package ch.squix.extraleague.model.ranking.tasks;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Calendar;
import java.util.Map;

import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.mutations.PlayerMutation;
import ch.squix.extraleague.model.mutations.tasks.MutationTask;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

public class DeltaRankingTask implements RankingTask {


	@Override
	public void rankMatches(Map<String, PlayerRanking> playerRankingMap, Matches matches) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Ranking dayStartRanking = ofy().load().type(Ranking.class).order("createdDate").filter("createdDate > ", calendar.getTime()).limit(1).first().now();
		if (dayStartRanking != null) {
			for (PlayerRanking newPlayerRanking : playerRankingMap.values()) {
				PlayerRanking oldPlayerRanking = dayStartRanking.getPlayerRanking(newPlayerRanking.getPlayer());
				Integer newRank = newPlayerRanking.getEloRanking();
				Integer newElo = newPlayerRanking.getEloValue();
				if (oldPlayerRanking != null) {
					Integer oldRank = oldPlayerRanking.getEloRanking();
					newPlayerRanking.setRankingDelta(oldRank - newRank);
					Integer oldElo = oldPlayerRanking.getEloValue();
					newPlayerRanking.setEloDelta(newElo - oldElo);
				}

			}
		}
	}

}

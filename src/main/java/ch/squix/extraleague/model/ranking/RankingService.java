package ch.squix.extraleague.model.ranking;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.ranking.tasks.AverageTimePerMatchTask;
import ch.squix.extraleague.model.ranking.tasks.BestPositionTask;
import ch.squix.extraleague.model.ranking.tasks.CurrentShapeTask;
import ch.squix.extraleague.model.ranking.tasks.DynamicRankingIndexTask;
import ch.squix.extraleague.model.ranking.tasks.EloRankingTask;
import ch.squix.extraleague.model.ranking.tasks.FirstPlayerFilterTask;
import ch.squix.extraleague.model.ranking.tasks.IncestuousTask;
import ch.squix.extraleague.model.ranking.tasks.ManualBadgeTask;
import ch.squix.extraleague.model.ranking.tasks.PartnerCountTask;
import ch.squix.extraleague.model.ranking.tasks.PartnerOpponentTask;
import ch.squix.extraleague.model.ranking.tasks.PlayerGoalsTask;
import ch.squix.extraleague.model.ranking.tasks.RankingIndexTask;
import ch.squix.extraleague.model.ranking.tasks.RankingTask;
import ch.squix.extraleague.model.ranking.tasks.ScoreTask;
import ch.squix.extraleague.model.ranking.tasks.SkillBadgesTask;
import ch.squix.extraleague.model.ranking.tasks.SlamTask;
import ch.squix.extraleague.model.ranking.tasks.SpecialResultPerGameTask;
import ch.squix.extraleague.model.ranking.tasks.StrikeTask;
import ch.squix.extraleague.model.ranking.tasks.TightMatchesTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class RankingService {
	
	private static final Logger log = Logger.getLogger(RankingService.class.getName());

	public static void calculateRankings() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		List<Match> matchesList = ofy().load().type(Match.class).filter("startDate > ", calendar.getTime()).list();
		Matches matches = new Matches();
		matches.setMatches(matchesList);
		
		// Initialize player ranking map
		Map<String, PlayerRanking> playerRankingMap = new HashMap<>();
		for (String player : matches.getPlayers()) {
	                PlayerRanking ranking = new PlayerRanking();
	                ranking.setPlayer(player);
	                ranking.setGamesWon(0);
	                ranking.setGamesLost(0);
	                playerRankingMap.put(player, ranking);
		}
		
		List<RankingTask> rankingTasks = new ArrayList<>();
		rankingTasks.add(new ScoreTask());
		rankingTasks.add(new SpecialResultPerGameTask());
		rankingTasks.add(new BestPositionTask());
		rankingTasks.add(new SlamTask());
		rankingTasks.add(new StrikeTask());
		rankingTasks.add(new PartnerOpponentTask());
		rankingTasks.add(new AverageTimePerMatchTask());
		rankingTasks.add(new ManualBadgeTask());
		rankingTasks.add(new CurrentShapeTask());
		rankingTasks.add(new IncestuousTask());
		rankingTasks.add(new TightMatchesTask());
		rankingTasks.add(new PlayerGoalsTask());
        rankingTasks.add(new DynamicRankingIndexTask());
        rankingTasks.add(new EloRankingTask());

		// From here only work on playerRankingMap
		rankingTasks.add(new FirstPlayerFilterTask());
		rankingTasks.add(new RankingIndexTask());
		rankingTasks.add(new SkillBadgesTask());
		rankingTasks.add(new PartnerCountTask());

		for (RankingTask task: rankingTasks) {
		    task.rankMatches(playerRankingMap, matches);
		}
		
		List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
		Ranking ranking = new Ranking();
		ranking.setCreatedDate(new Date());
		ranking.setPlayerRankings(rankings);

		
		ofy().save().entities(ranking);

	}




}

package ch.squix.extraleague.model.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.Matches;
import ch.squix.extraleague.model.mutations.MutationService;
import ch.squix.extraleague.model.ranking.tasks.AchievementPointTask;
import ch.squix.extraleague.model.ranking.tasks.AverageTimePerMatchTask;
import ch.squix.extraleague.model.ranking.tasks.BestPositionTask;
import ch.squix.extraleague.model.ranking.tasks.CurrentShapeTask;
import ch.squix.extraleague.model.ranking.tasks.DeltaRankingTask;
import ch.squix.extraleague.model.ranking.tasks.EloRankingTask;
import ch.squix.extraleague.model.ranking.tasks.FirstPlayerFilterTask;
import ch.squix.extraleague.model.ranking.tasks.GoalsPerGameTask;
import ch.squix.extraleague.model.ranking.tasks.IncestuousTask;
import ch.squix.extraleague.model.ranking.tasks.ManualBadgeTask;
import ch.squix.extraleague.model.ranking.tasks.PartnerCountTask;
import ch.squix.extraleague.model.ranking.tasks.PartnerOpponentTask;
import ch.squix.extraleague.model.ranking.tasks.PlayerGoalsTask;
import ch.squix.extraleague.model.ranking.tasks.ProjectLeaderTask;
import ch.squix.extraleague.model.ranking.tasks.RankingIndexTask;
import ch.squix.extraleague.model.ranking.tasks.RankingTask;
import ch.squix.extraleague.model.ranking.tasks.ScoreHistogramTask;
import ch.squix.extraleague.model.ranking.tasks.ScoreTask;
import ch.squix.extraleague.model.ranking.tasks.SkillBadgesTask;
import ch.squix.extraleague.model.ranking.tasks.SlamTask;
import ch.squix.extraleague.model.ranking.tasks.SpecialResultPerGameTask;
import ch.squix.extraleague.model.ranking.tasks.StrikeTask;
import ch.squix.extraleague.model.ranking.tasks.TightMatchesTask;
import ch.squix.extraleague.model.ranking.tasks.TrueSkillRankingTask;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateRankingMessage;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;

public class RankingService {

    private static final Logger log = Logger.getLogger(RankingService.class.getName());

    public static void calculateRankings() { 
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        List<Match> matchesList = ofy().load()
                .type(Match.class)
                .filter("startDate > ", calendar.getTime())
                .list();
        
        Ranking newRanking = calculateRankingFromMatches(matchesList);
        Ranking previousRanking = ofy().load().type(Ranking.class).order("-createdDate").limit(1).first().now();

        if (previousRanking != null && newRanking != null) {
                MutationService.calculateMutations(previousRanking, newRanking);
        }
        
        NotificationService.sendMessage(new UpdateRankingMessage());

        ofy().save().entities(newRanking).now();

    }
    
    public static void calculateEternalRankings() {
        List<Match> matchesList = ofy().load()
                .type(Match.class)
                .list();
        Ranking ranking = calculateRankingFromMatches(matchesList);
        EternalRanking eternalRanking = ofy().load().type(EternalRanking.class).first().now();
        if (eternalRanking == null) {
        	eternalRanking = new EternalRanking();
        }
        eternalRanking.setPlayerRankings(ranking.getPlayerRankings());
        ofy().save().entities(eternalRanking).now();	
    }

    public static Ranking calculateRankingFromMatches(List<Match> matchesList) {
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
        rankingTasks.add(new EloRankingTask());
        rankingTasks.add(new TrueSkillRankingTask());
        rankingTasks.add(new ScoreHistogramTask());
        rankingTasks.add(new GoalsPerGameTask());
        rankingTasks.add(new ProjectLeaderTask());

        // From here only work on playerRankingMap
        //rankingTasks.add(new FirstPlayerFilterTask());
        rankingTasks.add(new RankingIndexTask());
        rankingTasks.add(new SkillBadgesTask());
        rankingTasks.add(new PartnerCountTask());
        rankingTasks.add(new AchievementPointTask());
        rankingTasks.add(new DeltaRankingTask());

        for (RankingTask task : rankingTasks) {
            task.rankMatches(playerRankingMap, matches);
        }

        List<PlayerRanking> rankings = new ArrayList<>(playerRankingMap.values());
        Ranking ranking = new Ranking();
        ranking.setCreatedDate(new Date());
        ranking.setPlayerRankings(rankings);
        return ranking;
    }


}

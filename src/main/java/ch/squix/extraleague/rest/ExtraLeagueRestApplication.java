package ch.squix.extraleague.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.statistics.Statistics;
import ch.squix.extraleague.rest.admin.users.PlayerUserAdminResource;
import ch.squix.extraleague.rest.admin.users.PlayerUsersAdminResource;
import ch.squix.extraleague.rest.badges.BadgesResource;
import ch.squix.extraleague.rest.games.GameResource;
import ch.squix.extraleague.rest.games.GamesResource;
import ch.squix.extraleague.rest.games.OpenGamesResource;
import ch.squix.extraleague.rest.games.PlayedGamesResource;
import ch.squix.extraleague.rest.maintenance.MigrateMatchesResource;
import ch.squix.extraleague.rest.matches.MatchesResource;
import ch.squix.extraleague.rest.mutations.MutationsResource;
import ch.squix.extraleague.rest.network.PlayerNetworkResource;
import ch.squix.extraleague.rest.notification.NotificationTokenResource;
import ch.squix.extraleague.rest.ping.PingResource;
import ch.squix.extraleague.rest.player.PlayerRessource;
import ch.squix.extraleague.rest.player.PlayersRessource;
import ch.squix.extraleague.rest.playermarket.MeetingPointPlayerResource;
import ch.squix.extraleague.rest.playermarket.MeetingPointPlayersResource;
import ch.squix.extraleague.rest.playeruser.PlayerUserResource;
import ch.squix.extraleague.rest.ranking.CleanInDayRankingsResource;
import ch.squix.extraleague.rest.ranking.RankingByTagResource;
import ch.squix.extraleague.rest.ranking.RankingResource;
import ch.squix.extraleague.rest.ranking.RankingServiceResource;
import ch.squix.extraleague.rest.result.SummaryResource;
import ch.squix.extraleague.rest.statistics.StatisticsResource;
import ch.squix.extraleague.rest.statistics.UpdateStatisticsResource;
import ch.squix.extraleague.rest.tables.TablesResource;
import ch.squix.extraleague.rest.timeseries.TimeSeriesResource;

import com.googlecode.objectify.ObjectifyService;

public class ExtraLeagueRestApplication extends Application {
	
    static {
        ObjectifyService.register(Game.class);
        ObjectifyService.register(Match.class);
        ObjectifyService.register(Ranking.class);
        ObjectifyService.register(BrowserClient.class);
        ObjectifyService.register(Statistics.class);
        ObjectifyService.register(Mutations.class);
        ObjectifyService.register(PlayerUser.class);
        ObjectifyService.register(MeetingPointPlayer.class);
    }
	
	@Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        router.attach("/ping", PingResource.class);
        router.attach("/tables", TablesResource.class);
        router.attach("/ranking", RankingResource.class);
        router.attach("/rankings/tags/{tag}", RankingByTagResource.class);
        router.attach("/tables/{table}/games", GamesResource.class);
        router.attach("/openGames", OpenGamesResource.class);
        router.attach("/playedGames", PlayedGamesResource.class);
        router.attach("/games/{gameId}", GameResource.class);
        router.attach("/games/{gameId}/matches", MatchesResource.class);
        router.attach("/games/{gameId}/summary", SummaryResource.class);
        router.attach("/players", PlayersRessource.class);
        router.attach("/players/{player}", PlayerRessource.class);
        router.attach("/timeseries/{player}", TimeSeriesResource.class);
        router.attach("/meetingPointPlayers", MeetingPointPlayersResource.class);
        router.attach("/meetingPointPlayers/{playerId}", MeetingPointPlayerResource.class);
        router.attach("/updateRankings", RankingServiceResource.class);
        router.attach("/cleanRankings", CleanInDayRankingsResource.class);
        router.attach("/updateStatistics", UpdateStatisticsResource.class);
        router.attach("/statistics", StatisticsResource.class);
        router.attach("/migrateMatches", MigrateMatchesResource.class);
        router.attach("/notificationToken", NotificationTokenResource.class);
        router.attach("/badges", BadgesResource.class);
        router.attach("/playerNetwork", PlayerNetworkResource.class);
        router.attach("/mutations", MutationsResource.class);
        router.attach("/admin/playerUsers", PlayerUsersAdminResource.class);
        router.attach("/admin/playerUsers/{player}", PlayerUserAdminResource.class);
        router.attach("/playerUsers", PlayerUserResource.class);
        

        return router;
    }

}

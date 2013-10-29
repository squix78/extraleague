package ch.squix.extraleague.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GameResource;
import ch.squix.extraleague.rest.games.GamesResource;
import ch.squix.extraleague.rest.ping.PingResource;
import ch.squix.extraleague.rest.tables.TablesResource;

import com.googlecode.objectify.ObjectifyService;

public class ExtraLeagueRestApplication extends Application {
	
    static {
        ObjectifyService.register(Game.class);
        ObjectifyService.register(Match.class);
    }
	
	@Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        router.attach("/ping", PingResource.class);
        router.attach("/tables", TablesResource.class);
        router.attach("/tables/{table}/games", GamesResource.class);
        router.attach("/tables/{table}/games/{gameId}", GameResource.class);
        router.attach("/tables/{table}/games/{gameId}/matches", GameResource.class);

        return router;
    }

}

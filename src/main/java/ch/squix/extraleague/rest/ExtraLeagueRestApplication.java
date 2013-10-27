package ch.squix.extraleague.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ExtraLeagueRestApplication extends Application {
	
    static {
//        ObjectifyService.register(PairRate.class);
//        ObjectifyService.register(SpeakerRate.class);
//        ObjectifyService.register(Highscore.class);
    }
	
	@Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        //router.attach("/admin/maintenance", MaintenanceResource.class);
//        router.attach("/tweets/{paperID}", TweetListResource.class);
        return router;
    }

}

package ch.squix.extraleague.rest.ranking;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.NamespaceManager;

import ch.squix.extraleague.model.ranking.RankingService;

public class UpdateEternalRankingsResource extends ServerResource {

    private static final Logger log = Logger.getLogger(UpdateEternalRankingsResource.class.getName());

    @Get(value = "json")
    public String execute() throws UnsupportedEncodingException {
        log.info("Calculating ranking for namespace: " + NamespaceManager.get());
        RankingService.calculateEternalRankings();
        return "OK";

    }


}

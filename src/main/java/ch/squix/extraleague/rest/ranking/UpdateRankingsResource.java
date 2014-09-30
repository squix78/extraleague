package ch.squix.extraleague.rest.ranking;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.RankingService;

import com.google.appengine.api.NamespaceManager;

public class UpdateRankingsResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(UpdateRankingsResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
            log.info("Calculating ranking for namespace: " + NamespaceManager.get());
            RankingService.calculateRankings();
            
            return "OK";
	}


}
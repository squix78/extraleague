package ch.squix.extraleague.rest.league;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class LeagueStyleResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(LeagueStyleResource.class.getName());
	
	@Get(value = "css")
	public String execute() throws UnsupportedEncodingException {
		League league = LeagueDao.getCurrentLeague();
		if (league == null) {
			log.info("No league found");
			return "";
		}
		return league.getLeagueCss();
	}

}

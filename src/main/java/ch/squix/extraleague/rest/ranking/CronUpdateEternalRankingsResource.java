package ch.squix.extraleague.rest.ranking;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.LeagueDao;

public class CronUpdateEternalRankingsResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		LeagueDao.runCronOverNamespaces("/rest/admin/updateEternalRankings");
		
		return "OK";
	}


}
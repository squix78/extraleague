package ch.squix.extraleague.server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class AddLeagueResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		String leagueName = (String) this.getRequestAttributes().get("leagueName");
		String domain = (String) this.getRequestAttributes().get("domain");
		League league = new League();
		league.setDomain(domain);
		league.setName(leagueName);
		league.setWebhookUrl("https://qrlee.extranet.netcetera.biz/secure/gameFinished/");
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Auth-Token", "CNI_LMU_MKE_SL_TMI");
		league.setRequestHeaders(requestHeaders);

		LeagueDao.saveLeague(league);
		return "OK";

	}

}

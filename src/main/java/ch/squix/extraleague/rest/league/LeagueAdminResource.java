package ch.squix.extraleague.rest.league;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class LeagueAdminResource extends ServerResource {
	
	@Get(value = "json")
	public LeagueDto execute() throws UnsupportedEncodingException {
		League league = LeagueDao.getCurrentLeague();
		if (league == null) {
			return null;
		}
		LeagueDto dto = new LeagueDto();
		dto.setDomain(league.getDomain());
		dto.setName(league.getName());
		dto.setWebhookUrl(league.getWebhookUrl());
		dto.setTables(league.getTables());
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> entry : league.getRequestHeaders().entrySet()) {
			builder.append(entry.getKey());
			builder.append(": ");
			builder.append(entry.getValue());
			builder.append("\n");
		}
		dto.setRequestHeaders(builder.toString());
		return dto;
	}
	
	@Post(value = "json")
	public void update(LeagueDto dto) {
		League league = LeagueDao.getCurrentLeague();
		league.setDomain(dto.getDomain());
		league.setName(dto.getName());
		league.setWebhookUrl(dto.getWebhookUrl());
		league.setTables(dto.getTables());
	    String requestHeaders = dto.getRequestHeaders();
	    String [] lines = requestHeaders.split("\n");
	    Map<String, String> requestHeaderMap = new HashMap<>();
	    for (String line : lines) {
	    	String [] tokens = line.split(":[ ]{0,1}");
	    	requestHeaderMap.put(tokens[0], tokens[1]);
	    }
	    league.setRequestHeaders(requestHeaderMap);
	    LeagueDao.saveLeague(league);
		
	}

}

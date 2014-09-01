package ch.squix.extraleague.rest.league;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class LeagueResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(LeagueResource.class.getName());
	
	@Get(value = "json")
	public LeagueDto execute() throws UnsupportedEncodingException {
		League league = LeagueDao.getCurrentLeague();
		if (league == null) {
			log.info("No league found");
			return null;
		}
		LeagueDto dto = new LeagueDto();
		dto.setDomain(league.getDomain());
		dto.setName(league.getName());
		dto.setLogoUrl(league.getLogoUrl());
		return dto;
	}


}

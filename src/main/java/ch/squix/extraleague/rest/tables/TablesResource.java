package ch.squix.extraleague.rest.tables;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class TablesResource extends ServerResource {
	
	@Get(value = "json")
	public List<TablesDto> execute() throws UnsupportedEncodingException {
		League currentLeague = LeagueDao.getCurrentLeague();
		List<TablesDto> dtos = new ArrayList<>();
		for (String table : currentLeague.getTables()) {
			TablesDto dto = new TablesDto();
			dto.setName(table);
			dtos.add(dto);
		}
		return dtos;
//		return new TablesDto[] {
//				new TablesDto("Park"), 
//				new TablesDto("Albis"), 
//				new TablesDto("Bern"), 
//				new TablesDto("Skopje"), 
//				new TablesDto("Flurstrasse")};
	}

}

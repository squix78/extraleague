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
		List<String> tables = currentLeague.getTables();
		if (tables.size() == 0) {
			tables.add("Table");
		}
		List<TablesDto> dtos = new ArrayList<>();
		for (String table : tables) {
			TablesDto dto = new TablesDto();
			dto.setName(table);
			dtos.add(dto);
		}
		
		return dtos;
	} 

}

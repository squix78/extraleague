package ch.squix.extraleague.rest.challenger;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.challenger.WinnerTeam;



public class WinnerTeamResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(WinnerTeamResource.class.getName());
	
	@Get(value = "json")
	public WinnerTeamDto execute() throws UnsupportedEncodingException {
		String table = (String) this.getRequestAttributes().get("table");
		WinnerTeam team = ofy().load().type(WinnerTeam.class).filter("table = ", table).first().now();
		if (team == null) {
			log.info("No winner team for this table found");
			return null;
		}
		return WinnerTeamDtoMapper.mapToDto(team);
	}


}

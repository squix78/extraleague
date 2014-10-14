package ch.squix.extraleague.rest.challenger;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.challenger.ChallengerTeam;

import com.google.appengine.repackaged.com.google.common.base.Strings;
import com.google.common.base.Joiner;
import com.googlecode.objectify.Key;



public class ChallengerTeamsResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(ChallengerTeamsResource.class.getName());
	
	@Get(value = "json")
	public List<ChallengerTeamDto> execute() throws UnsupportedEncodingException {
		String table = (String) this.getRequestAttributes().get("table");
		List<ChallengerTeam> teams = ofy().load().type(ChallengerTeam.class).filter("table = ", table).list();
		return ChallengerTeamDtoMapper.mapToDtos(teams);
	}
	
	@Post(value = "json")
	public ChallengerTeamDto create(ChallengerTeamDto dto) {
		ChallengerTeam team = new ChallengerTeam();
		team.setCreatedDate(new Date());
		team.setChallengers(dto.getChallengers());
		team.setWinners(dto.getWinners());
		team.setTable(dto.getTable());
		Key<ChallengerTeam> teamKey = ofy().save().entity(team).now();
		dto.setId(teamKey.getId());
		return dto;
	}
	
	@Delete(value = "json")
	public void deleteTeam() {
		String idText = (String) this.getRequestAttributes().get("id");
		ofy().delete().type(ChallengerTeam.class).id(Long.valueOf(idText)).now();
	}



}

package ch.squix.extraleague.rest.matches;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GamesResource;



public class MatchExportResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "text/csv")
	public List<MatchDto> execute() throws UnsupportedEncodingException {

		List<Match> matches = ofy().load().type(Match.class).list();
		log.info("Loaded number of matches: " + matches.size());
		return MatchDtoMapper.mapToDtoList(matches);

	}


}

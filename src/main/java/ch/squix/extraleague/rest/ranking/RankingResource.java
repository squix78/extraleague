package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.Ranking;

import com.google.appengine.api.NamespaceManager;

public class RankingResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(RankingResource.class.getName());

	
	@Get(value = "json")
	public List<RankingDto> execute() throws UnsupportedEncodingException {
		log.info("Requesting ranking for namespace: " + NamespaceManager.get());
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		if (ranking == null) {
			return new ArrayList<>();
		}
		return RankingDtoMapper.convertToDto(ranking);
	}




}
package ch.squix.extraleague.rest.mutations;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.mutations.PlayerMutation;



public class MutationsResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(MutationsResource.class.getName());
	
	@Get(value = "json")
	public List<PlayerMutationDto> execute() throws UnsupportedEncodingException {
		List<PlayerMutation> mutations = ofy().load().type(PlayerMutation.class).order("-createdDate").limit(15).list();
		if (mutations == null) {
			log.info("No mutations found");
			return new ArrayList<>();
		}
		return MutationDtoMapper.mapToDtos(mutations);
		
	}



}

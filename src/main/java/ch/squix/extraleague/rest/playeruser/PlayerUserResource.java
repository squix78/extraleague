package ch.squix.extraleague.rest.playeruser;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.games.OpenGamesResource;
import ch.squix.extraleague.rest.playeruser.PlayerUserDto;
import ch.squix.extraleague.rest.playeruser.PlayerUserDtoMapper;
import ch.squix.extraleague.rest.playeruser.PlayerUserListDto;



public class PlayerUserResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public List<PlayerUserDto> executeGet() {
		List<PlayerUser> players = ofy().load().type(PlayerUser.class).list();
		return PlayerUserDtoMapper.mapToDtos(players);
	}

}

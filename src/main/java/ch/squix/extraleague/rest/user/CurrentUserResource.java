package ch.squix.extraleague.rest.user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.games.OpenGamesResource;
import ch.squix.extraleague.rest.playeruser.PlayerUserDto;
import ch.squix.extraleague.rest.playeruser.PlayerUserDtoMapper;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



public class CurrentUserResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public PlayerUserDto executeGet() {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		String userId = currentUser.getUserId();
		PlayerUser player = ofy().load().type(PlayerUser.class).filter("appUserId = ", userId).first().now();
		if (player == null) {
			return null;
		}
		return PlayerUserDtoMapper.mapToDto(player);
	}

}

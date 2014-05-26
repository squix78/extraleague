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



public class LoginLogoutResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public UrlDto executeGet() {
		UserService userService = UserServiceFactory.getUserService();
		UrlDto dto = new UrlDto();
		if (userService.isUserLoggedIn()) {
			dto.setUrl(userService.createLogoutURL("/"));
		} else {
			dto.setUrl(userService.createLoginURL("/user"));
		}
		return dto;
	}

}

package ch.squix.extraleague.rest.user;

import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.rest.games.OpenGamesResource;

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
			dto.setUrl(userService.createLoginURL("/user/"));
		}
		return dto;
	}

}

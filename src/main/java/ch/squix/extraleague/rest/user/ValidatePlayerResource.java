package ch.squix.extraleague.rest.user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.games.OpenGamesResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Strings;



public class ValidatePlayerResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Post(value = "json")
	public ValidatePlayerDto validate(ValidatePlayerDto dto) {

		PlayerUser playerToClaim = ofy().load().type(PlayerUser.class).filter("player = ", dto.getValue()).first().now();
		if (playerToClaim == null 
				|| ((playerToClaim != null && Strings.isNullOrEmpty(playerToClaim.getAppUserId())))) {
			dto.setIsValid(true);
		} else {
			dto.setIsValid(true);
		}

		return dto;
	}

}

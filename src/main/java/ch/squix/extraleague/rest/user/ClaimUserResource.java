package ch.squix.extraleague.rest.user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.rest.games.OpenGamesResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



public class ClaimUserResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public String executeGet() {
		String player = (String) this.getRequestAttributes().get("player");
		
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		String userId = currentUser.getUserId();
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("player = ", player).first().now();
		if (playerUser == null) {
			this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return "User not found";
		}
		if (playerUser.getAppUserId() != null) {
			this.setStatus(Status.CLIENT_ERROR_CONFLICT);
			return "Already claimed";
		}
		playerUser.setAppUserId(userId);
		playerUser.setAppUserEmail(currentUser.getEmail());
		ofy().save().entity(playerUser).now();
		NotificationService.sendAdminMessage("User claimed", "GMail: " + currentUser.getEmail() + ", claimed player: " + playerUser.getPlayer());
		this.setStatus(Status.SUCCESS_OK);
		return "OK";
	}

}

package ch.squix.extraleague.rest.user;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.playeruser.PlayerUserDto;
import ch.squix.extraleague.rest.playeruser.PlayerUserDtoMapper;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



public class CurrentUserResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(CurrentUserResource.class.getName());
	
	@Get(value = "json")
	public PlayerUserDto executeGet() {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		PlayerUserDto dto = new PlayerUserDto();
		if (userService.isUserLoggedIn()) {
				
			String userId = currentUser.getUserId();
			PlayerUser player = ofy().load().type(PlayerUser.class).filter("appUserId", userId).first().now();
			if (player != null) {
				dto = PlayerUserDtoMapper.mapToDto(player);
			} 
			dto.setAppUserEmail(currentUser.getEmail());
			dto.setNickname(currentUser.getNickname());
			dto.setFederatedIdentity(currentUser.getFederatedIdentity());
		}  
		dto.setLoggedIn(userService.isUserLoggedIn());
		dto.setLoginUrl(userService.createLoginURL("/#/account/"));
		dto.setLogoutUrl(userService.createLogoutURL("/"));
		return dto;
	}
	
	@Post(value = "json")
	public PlayerUserDto executePost(PlayerUserDto dto) {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		if (currentUser != null 
				&& dto.getAppUserEmail() != null 
				&& dto.getAppUserEmail().equals(currentUser.getEmail())) {
			return PlayerUserDtoMapper.savePlayerUser(dto, currentUser);
		}
		getResponse().setStatus(Status.CLIENT_ERROR_CONFLICT);
		return null;
	}

}

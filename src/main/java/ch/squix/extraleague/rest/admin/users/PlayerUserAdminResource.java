package ch.squix.extraleague.rest.admin.users;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.games.OpenGamesResource;
import ch.squix.extraleague.rest.playeruser.PlayerUserDto;
import ch.squix.extraleague.rest.playeruser.PlayerUserDtoMapper;

import com.googlecode.objectify.Key;



public class PlayerUserAdminResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public PlayerUserDto executeGet() {
		String player = (String) this.getRequestAttributes().get("player");
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("player == ", player).first().now();
		if (playerUser != null) {
			return PlayerUserDtoMapper.mapToAdminDto(playerUser);
		}
		return null;
	}
	
	@Post(value = "json")
	public void executePost(PlayerUserDto playerUserDto) {
		String player = (String) this.getRequestAttributes().get("player");
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("player == ", player).first().now();
		if (playerUser == null) {
			playerUser = new PlayerUser();
			playerUser.setPlayer(player);
		}
		playerUser.setEmail(playerUserDto.getEmail());
		playerUser.setImageUrl(playerUserDto.getImageUrl());
		playerUser.setEmailNotification(playerUserDto.getEmailNotification());
		ofy().save().entities(playerUser).now();
	}
	
	@Delete(value = "json")
	public void executePost() {
		String player = (String) this.getRequestAttributes().get("player");
		Key<PlayerUser> playerKey = ofy().load().type(PlayerUser.class).filter("player == ", player).keys().first().now();
		ofy().delete().key(playerKey).now();
	}



}

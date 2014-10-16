package ch.squix.extraleague.rest.playeruser;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Key;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.notification.NewPlayerRegisteredMessage;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.rest.games.OpenGamesResource;



public class PlayerUserResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public List<PlayerUserDto> executeGet() {
		List<PlayerUser> players = ofy().load().type(PlayerUser.class).list();
		return PlayerUserDtoMapper.mapToDtos(players);
	}
	
	@Post(value = "json")
	public PlayerUserDto createPlayer(PlayerUserDto dto) {
		PlayerUser player = ofy().load().type(PlayerUser.class).filter("player = ", dto.getPlayer()).first().now();
		if (player != null) {
			getResponse().setStatus(Status.CLIENT_ERROR_CONFLICT, "Player already exists!");
		}
		log.info("Creating new player: " + dto.getPlayer());
		player = new PlayerUser();
		player.setPlayer(dto.getPlayer());
		player.setEmail(dto.getEmail());
		player.setImageUrl(dto.getImageUrl());
		ofy().save().entity(player).now();
		NotificationService.sendMessage(new NewPlayerRegisteredMessage(dto));
		return dto;
		
	}

}

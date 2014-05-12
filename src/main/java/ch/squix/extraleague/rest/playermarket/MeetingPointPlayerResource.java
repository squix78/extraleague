package ch.squix.extraleague.rest.playermarket;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMeetingPointMessage;
import ch.squix.extraleague.rest.games.OpenGamesResource;



public class MeetingPointPlayerResource extends ServerResource {
	private static final Logger log = Logger.getLogger(MeetingPointPlayerResource.class.getName());
	

	
	@Delete(value = "json")
	public void deletePlayerInMarket() {
		String playerIdText = (String) this.getRequestAttributes().get("playerId");
		Long playerId = Long.valueOf(playerIdText);
		log.info("deleting user with id " + playerId);
		ofy().delete().type(MeetingPointPlayer.class).id(playerId).now();
		List<MeetingPointPlayer> players = ofy().load().type(MeetingPointPlayer.class).filter("availableUntil >", new Date()).list();
		NotificationService.sendMessage(new UpdateMeetingPointMessage(MeetingPointPlayerMapper.mapToDtos(players)));
	}

}

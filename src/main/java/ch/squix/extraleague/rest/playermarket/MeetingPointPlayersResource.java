package ch.squix.extraleague.rest.playermarket;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.common.base.Joiner;

import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMeetingPointMessage;
import ch.squix.extraleague.rest.games.OpenGamesResource;



public class MeetingPointPlayersResource extends ServerResource {
	private static final Logger log = Logger.getLogger(MeetingPointPlayersResource.class.getName());
	
	@Get(value = "json")
	public List<MeetingPointPlayerDto> execute() throws UnsupportedEncodingException {
		List<MeetingPointPlayer> players = getCurrentPlayersInMarket();

		return MeetingPointPlayerMapper.mapToDtos(players);
	}

	private List<MeetingPointPlayer> getCurrentPlayersInMarket() {
		return ofy().load().type(MeetingPointPlayer.class).filter("availableUntil >", new Date()).list();
	}
	
	@Post(value = "json")
	public void createPlayerInMarket(MeetingPointPlayerDto playerDto) {
		MeetingPointPlayer player = MeetingPointPlayerMapper.mapFromDto(playerDto);
		ofy().save().entity(player).now();
		List<MeetingPointPlayer> players = getCurrentPlayersInMarket();
		NotificationService.sendMessage(new UpdateMeetingPointMessage(MeetingPointPlayerMapper.mapToDtos(players)));
		List<String> playerShortNames = new ArrayList<>();
		for (MeetingPointPlayer meetingPointPlayer : players) {
			playerShortNames.add(meetingPointPlayer.getPlayer());
		}
		
		NotificationService.sendMeetingPointMessage(
				"New player at meeting point: " + playerDto.getPlayer(), 
				"Now waiting: " + Joiner.on(", ").join(playerShortNames));
	}

}

package ch.squix.extraleague.rest.playermarket;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMeetingPointMessage;



public class MeetingPointPlayerResource extends ServerResource {
	
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
	}
	
	@Delete(value = "json")
	public void deletePlayerInMarket(MeetingPointPlayerDto playerDto) {
		ofy().delete().type(MeetingPointPlayer.class).id(playerDto.getId()).now();
		List<MeetingPointPlayer> players = getCurrentPlayersInMarket();
		NotificationService.sendMessage(new UpdateMeetingPointMessage(MeetingPointPlayerMapper.mapToDtos(players)));
	}

}

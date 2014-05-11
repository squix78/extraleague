package ch.squix.extraleague.notification;

import java.util.List;

import ch.squix.extraleague.rest.playermarket.MeetingPointPlayerDto;

public class UpdateMeetingPointMessage implements NotificationMessage {

	private final static String UPDATE_MEETING_POINT = "UpdateMeetingPoint";
	private List<MeetingPointPlayerDto> players;
	
	public UpdateMeetingPointMessage(List<MeetingPointPlayerDto> players) {
		this.players = players;
	}
	
	@Override
	public String getChannel() {
		return UPDATE_MEETING_POINT;
	}

	public List<MeetingPointPlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<MeetingPointPlayerDto> players) {
		this.players = players;
	}

}

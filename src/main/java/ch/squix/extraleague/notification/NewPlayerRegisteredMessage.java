package ch.squix.extraleague.notification;

import ch.squix.extraleague.rest.playeruser.PlayerUserDto;

public class NewPlayerRegisteredMessage implements NotificationMessage {

	private static final String CHANNEL = "NewPlayerRegistered";
	private PlayerUserDto player;

	public NewPlayerRegisteredMessage(PlayerUserDto player) {
		this.player = player;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
	
	public PlayerUserDto getPlayer() {
		return player;
	}

}

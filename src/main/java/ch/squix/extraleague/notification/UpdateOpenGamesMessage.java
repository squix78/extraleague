package ch.squix.extraleague.notification;

import java.util.List;

import ch.squix.extraleague.rest.games.GameDto;

public class UpdateOpenGamesMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateOpenGames";
	private List<GameDto> openGames;

	public UpdateOpenGamesMessage(List<GameDto> openGames) {
		this.openGames = openGames;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
	
	public List<GameDto> getOpenGames() {
		return openGames;
	}

}

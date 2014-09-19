package ch.squix.extraleague.notification;

import ch.squix.extraleague.rest.games.GameDto;

public class GameFinishedMessage implements NotificationMessage {

	private static final String CHANNEL = "GameFinished";
	private GameDto finishedGame;

	public GameFinishedMessage(GameDto finishedGame) {
		this.finishedGame = finishedGame;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
	
	public GameDto getFinishedGame() {
		return finishedGame;
	}

}

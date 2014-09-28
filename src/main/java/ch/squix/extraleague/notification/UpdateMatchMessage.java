package ch.squix.extraleague.notification;

import ch.squix.extraleague.rest.games.GameDto;
import ch.squix.extraleague.rest.matches.MatchDto;
import ch.squix.extraleague.rest.matches.MatchInfoDto;

public class UpdateMatchMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateMatch";
	private MatchDto match;
	private GameDto game;

	public UpdateMatchMessage(GameDto game, MatchDto match) {
		this.game = game;
		this.match = match;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
	
	public MatchDto getMatch() {
		return match;
	}

	/**
	 * @return the game
	 */
	public GameDto getGame() {
		return game;
	}



}

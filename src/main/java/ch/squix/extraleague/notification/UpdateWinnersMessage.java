package ch.squix.extraleague.notification;

import lombok.Data;

@Data
public class UpdateWinnersMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateWinners";
	private String table;
	
	public UpdateWinnersMessage() {
	}
	
	public UpdateWinnersMessage(String table) {
		this.table = table;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
}

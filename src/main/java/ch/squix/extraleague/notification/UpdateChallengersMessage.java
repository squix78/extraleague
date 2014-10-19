package ch.squix.extraleague.notification;

import lombok.Data;

@Data
public class UpdateChallengersMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateChallengers";
	private String table;
	
	public UpdateChallengersMessage() {
	}
	
	public UpdateChallengersMessage(String table) {
		this.table = table;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
}

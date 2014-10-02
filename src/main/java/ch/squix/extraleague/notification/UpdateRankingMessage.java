package ch.squix.extraleague.notification;


public class UpdateRankingMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateRankings";
	
	public UpdateRankingMessage() {
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
}

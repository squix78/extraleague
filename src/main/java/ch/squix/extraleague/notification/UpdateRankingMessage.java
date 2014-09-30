package ch.squix.extraleague.notification;

import java.util.List;

import ch.squix.extraleague.rest.ranking.RankingDto;

public class UpdateRankingMessage implements NotificationMessage {

	private static final String CHANNEL = "UpdateRankings";
	private List<RankingDto> rankings;
	
	public UpdateRankingMessage(List<RankingDto> rankings) {
		this.rankings = rankings;
	}
	
	@Override
	public String getChannel() {
		return CHANNEL;
	}
	
	public List<RankingDto> getRankings() {
		return rankings;
	}

}

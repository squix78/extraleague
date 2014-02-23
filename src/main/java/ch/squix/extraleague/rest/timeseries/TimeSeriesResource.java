package ch.squix.extraleague.rest.timeseries;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;



public class TimeSeriesResource extends ServerResource {
	
	@Get(value = "json")
	public TimeSeriesDto execute() throws UnsupportedEncodingException {
		String player = (String) this.getRequestAttributes().get("player");
		List<Ranking> rankings = ofy().load().type(Ranking.class).order("createdDate").list();
		TimeSeriesDto timeSeriesDto = new TimeSeriesDto();
		DataSeriesDto successRateValues = new DataSeriesDto("Success Rate");
		timeSeriesDto.getSuccessRate().add(successRateValues);
		DataSeriesDto rankingValues = new DataSeriesDto("Ranking");
		timeSeriesDto.getRanking().add(rankingValues);
		DataSeriesDto goalRate = new DataSeriesDto("Goal Rate");
		timeSeriesDto.getGoalRate().add(goalRate);
		for (Ranking ranking : rankings) {
			Long timeStamp = ranking.getCreatedDate().getTime();
			for (PlayerRanking playerRanking : ranking.getPlayerRankings()) {
				if (playerRanking.getPlayer().equals(player)) {
					successRateValues.addTuple(String.valueOf(timeStamp), String.valueOf(playerRanking.getSuccessRate()));
					rankingValues.addTuple(String.valueOf(timeStamp), String.valueOf(playerRanking.getRanking()));
					goalRate.addTuple(String.valueOf(timeStamp), String.valueOf(playerRanking.getGoalRate()));
				}
			}
		}
		
		return timeSeriesDto;
	}

}

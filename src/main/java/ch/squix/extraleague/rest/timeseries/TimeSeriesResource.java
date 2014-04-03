package ch.squix.extraleague.rest.timeseries;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.statistics.DataTuple;



public class TimeSeriesResource extends ServerResource {
	
	@Get(value = "json")
	public TimeSeriesDto execute() throws UnsupportedEncodingException {
		String player = (String) this.getRequestAttributes().get("player");
		List<Ranking> rankings = ofy().load().type(Ranking.class).order("createdDate").list();
		TimeSeriesDto timeSeriesDto = new TimeSeriesDto();

		for (Ranking ranking : rankings) {
			Date createdDate = ranking.getCreatedDate();
			for (PlayerRanking playerRanking : ranking.getPlayerRankings()) {
				if (playerRanking.getPlayer().equals(player)) {
					DataTuple<Date, Double> successRateTuple = new DataTuple<>(createdDate, playerRanking.getSuccessRate(), "");
					timeSeriesDto.getSuccessRateSeries().add(successRateTuple);
					DataTuple<Date, Double> goalRateTuple = new DataTuple<>(createdDate, playerRanking.getGoalRate(), "");
					timeSeriesDto.getGoalRateSeries().add(goalRateTuple);
					DataTuple<Date, Integer> rankingTuple = new DataTuple<>(createdDate, playerRanking.getRanking(), "");
					timeSeriesDto.getRankingSeries().add(rankingTuple);
				}
			}
		}
		
		return timeSeriesDto;
	}

}

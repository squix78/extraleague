package ch.squix.extraleague.rest.timeseries;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
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
		NumberFormat percentageFormatter = NumberFormat.getPercentInstance();
		NumberFormat numberFormatter =  NumberFormat.getInstance();
		numberFormatter.setMaximumFractionDigits(2);
		for (Ranking ranking : rankings) {
			Date createdDate = ranking.getCreatedDate();
			for (PlayerRanking playerRanking : ranking.getPlayerRankings()) {
				if (playerRanking.getPlayer().equals(player)) {
					
					Double successRate = playerRanking.getSuccessRate();
					if (successRate!= null) {
						DataTuple<Date, Double> successRateTuple = new DataTuple<>(
								createdDate, successRate, percentageFormatter.format(successRate));
						timeSeriesDto.getSuccessRateSeries().add(successRateTuple);
					}
					
					Double goalPlusMinus = playerRanking.getGoalPlusMinus();
					if (goalPlusMinus != null) {
						DataTuple<Date, Double> goalRateTuple = new DataTuple<>(
								createdDate, goalPlusMinus, numberFormatter.format(goalPlusMinus));
						timeSeriesDto.getGoalRateSeries().add(goalRateTuple);
					}
					
					Integer rankingValue = playerRanking.getRanking();
					if (rankingValue != null) {
						DataTuple<Date, Integer> rankingTuple = new DataTuple<>(createdDate, rankingValue, rankingValue + ". place");
						timeSeriesDto.getRankingSeries().add(rankingTuple);
					}
					
					Integer eloValue = playerRanking.getEloValue();
					if (eloValue != null) {
						DataTuple<Date, Integer> rankingTuple = new DataTuple<>(createdDate, eloValue, eloValue + " elo points");
						timeSeriesDto.getEloValueSeries().add(rankingTuple);
					}
					
					Double goalsPerMatch = playerRanking.getAverageGoalsPerMatch();
					if (goalsPerMatch != null) {
						DataTuple<Date, Double> gpmTuple = new DataTuple<>(createdDate, goalsPerMatch, numberFormatter.format(goalsPerMatch));
						timeSeriesDto.getGoalsPerMatchSeries().add(gpmTuple);
					}
					
					Double shape = playerRanking.getCurrentShapeRate();
					if (shape != null) {
						DataTuple<Date, Double> shapeTuple = new DataTuple<>(createdDate, shape, percentageFormatter.format(shape));
						timeSeriesDto.getShapeSeries().add(shapeTuple);
					}
				}
			}
		}
		
		return timeSeriesDto;
	}

}

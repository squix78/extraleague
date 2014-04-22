package ch.squix.extraleague.rest.timeseries;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.ranking.BadgeDto;
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
		List<BadgeDto> datedBadges = new ArrayList<>();
		Map<String, BadgeDto> badgeMap = new HashMap<>();
		Set<String> previousBadges = new HashSet<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
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
					
					Set<String> newBadges = new HashSet<>();
					newBadges.addAll(playerRanking.getBadges());
					newBadges.removeAll(previousBadges);
					Set<String> lostBadges = new HashSet<>();
					lostBadges.addAll(previousBadges);
					lostBadges.removeAll(playerRanking.getBadges());
					for (String badge : newBadges) {
						BadgeDto badgeDto = new BadgeDto();
						badgeDto.setContent(badge);
						badgeDto.setId(Long.valueOf(datedBadges.size()));
						badgeDto.setStart(format.format(ranking.getCreatedDate()));
						datedBadges.add(badgeDto);
						badgeMap.put(badge, badgeDto);
					}
					for (String badge : lostBadges) {
						BadgeDto badgeDto = badgeMap.remove(badge);
						if (badgeDto != null) {
							badgeDto.setEnd(format.format(ranking.getCreatedDate()));
							
						}
					}
					previousBadges = playerRanking.getBadges();
				}
			}
		}
		for (String badge : badgeMap.keySet()) {
			BadgeDto badgeDto = badgeMap.get(badge);
			badgeDto.setEnd(format.format(new Date()));
		}
		timeSeriesDto.setDatedBadges(datedBadges);
		return timeSeriesDto;
	}

}

package ch.squix.extraleague.rest.player;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.rest.matches.MatchDto;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.timeseries.TimeSeriesDto;

public class PlayerDto {
	
	private String player;
	private RankingDto statistics;
	private RankingDto dayEndStatistics;
	private TimeSeriesDto timeSeries;
	private List<MatchDto> lastMatches = new ArrayList<>();
	
	public String getPlayer() {
		return player;
	}
	public RankingDto getStatistics() {
		return statistics;
	}
	public TimeSeriesDto getTimeSeries() {
		return timeSeries;
	}
	public List<MatchDto> getLastMatches() {
		return lastMatches;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setStatistics(RankingDto ranking) {
		this.statistics = ranking;
	}
	public void setTimeSeries(TimeSeriesDto timeSeries) {
		this.timeSeries = timeSeries;
	}
	public void setLastMatches(List<MatchDto> lastMatches) {
		this.lastMatches = lastMatches;
	}
	public RankingDto getDayEndStatistics() {
		return dayEndStatistics;
	}
	public void setDayEndStatistics(RankingDto dayEndStatistics) {
		this.dayEndStatistics = dayEndStatistics;
	}

}

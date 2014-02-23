package ch.squix.extraleague.rest.timeseries;

import java.util.ArrayList;
import java.util.List;

public class TimeSeriesDto {
	
	private List<DataSeriesDto> successRate = new ArrayList<>();
	private List<DataSeriesDto> ranking = new ArrayList<>();
	private List<DataSeriesDto> goalRate = new ArrayList<>();
	public List<DataSeriesDto> getSuccessRate() {
		return successRate;
	}
	public List<DataSeriesDto> getRanking() {
		return ranking;
	}
	
	public List<DataSeriesDto> getGoalRate() {
		return goalRate;
	}
	public void setSuccessRate(List<DataSeriesDto> successRate) {
		this.successRate = successRate;
	}
	public void setRanking(List<DataSeriesDto> ranking) {
		this.ranking = ranking;
	}
	public void setGoalRate(List<DataSeriesDto> goalRate) {
		this.goalRate = goalRate;
	}


}

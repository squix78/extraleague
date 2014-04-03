package ch.squix.extraleague.rest.timeseries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.statistics.DataTuple;

public class TimeSeriesDto {
	
	private List<DataTuple<Date, Double>> successRateSeries = new ArrayList<>();
	private List<DataTuple<Date, Double>> goalRateSeries = new ArrayList<>();
	private List<DataTuple<Date, Integer>> rankingSeries= new ArrayList<>();
	private List<DataTuple<Date, Integer>> eloValueSeries = new ArrayList<>();
	private List<DataTuple<Date, Double>> goalsPerMatchSeries = new ArrayList<>();
	private List<DataTuple<Date, Double>> shapeSeries = new ArrayList<>();

	
	public List<DataTuple<Date, Double>> getSuccessRateSeries() {
		return successRateSeries;
	}
	public List<DataTuple<Date, Double>> getGoalRateSeries() {
		return goalRateSeries;
	}
	public List<DataTuple<Date, Integer>> getRankingSeries() {
		return rankingSeries;
	}
	public void setSuccessRateSeries(List<DataTuple<Date, Double>> successRateSeries) {
		this.successRateSeries = successRateSeries;
	}
	public void setGoalRateSeries(List<DataTuple<Date, Double>> goalRateSeries) {
		this.goalRateSeries = goalRateSeries;
	}
	public void setRankingSeries(List<DataTuple<Date, Integer>> rankingSeries) {
		this.rankingSeries = rankingSeries;
	}
	public List<DataTuple<Date, Integer>> getEloValueSeries() {
		return eloValueSeries;
	}
	public void setEloValueSeries(List<DataTuple<Date, Integer>> eloValueSeries) {
		this.eloValueSeries = eloValueSeries;
	}
	public List<DataTuple<Date, Double>> getGoalsPerMatchSeries() {
		return goalsPerMatchSeries;
	}
	public void setGoalsPerMatchSeries(List<DataTuple<Date, Double>> goalsPerMatchSeries) {
		this.goalsPerMatchSeries = goalsPerMatchSeries;
	}
	
	
	public List<DataTuple<Date, Double>> getShapeSeries() {
		return shapeSeries;
	}
	
	public void setShapeSeries(List<DataTuple<Date, Double>> shapeSeries) {
		this.shapeSeries = shapeSeries;
	}


}

package ch.squix.extraleague.rest.statistics;

import java.util.List;
import java.util.Map;

public class StatisticsDto {
	
	private List<DataTuple<Integer, Double>> hourHistogram;
	private List<DataTuple<Integer, Double>> successRateHistogram;
	private List<DataTuple<Integer, Double>> weekdayHistogram;

	/**
	 * @return the hourHistogram
	 */
	public List<DataTuple<Integer, Double>> getHourHistogram() {
		return hourHistogram;
	}

	/**
	 * @param hourHistogram the hourHistogram to set
	 */
	public void setHourHistogram(List<DataTuple<Integer, Double>> hourHistogram) {
		this.hourHistogram = hourHistogram;
	}

	public List<DataTuple<Integer, Double>> getSuccessRateHistogram() {
		return successRateHistogram;
	}

	public void setSuccessRateHistogram(List<DataTuple<Integer, Double>> successRateHistogram) {
		this.successRateHistogram = successRateHistogram;
	}

	public List<DataTuple<Integer, Double>> getWeekdayHistogram() {
		return weekdayHistogram;
	}

	public void setWeekdayHistogram(List<DataTuple<Integer, Double>> weekdayHistogram) {
		this.weekdayHistogram = weekdayHistogram;
	}




}

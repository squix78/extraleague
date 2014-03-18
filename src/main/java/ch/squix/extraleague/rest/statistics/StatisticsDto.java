package ch.squix.extraleague.rest.statistics;

import java.util.List;
import java.util.Map;

public class StatisticsDto {
	
	private List<DataTuple<Integer, Double>> hourHistogram;

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




}

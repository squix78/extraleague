package ch.squix.extraleague.rest.statistics;

import java.util.List;

import lombok.Data;

@Data
public class StatisticsDto {
	
	private List<DataTuple<Integer, Double>> hourHistogram;
	private List<DataTuple<Integer, Double>> successRateHistogram;
	private List<DataTuple<Integer, Double>> weekdayHistogram;
	private List<DataTuple<Integer, Double>> eloHistogram;






}

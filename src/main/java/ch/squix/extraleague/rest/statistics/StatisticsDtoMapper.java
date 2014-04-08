package ch.squix.extraleague.rest.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.squix.extraleague.model.statistics.Statistics;

public class StatisticsDtoMapper {
	
	public static StatisticsDto mapToDto(Statistics statistics) {
		StatisticsDto dto = new StatisticsDto();
		List<DataTuple<Integer, Double>> tuples = new ArrayList<>();
		for (Map.Entry<Integer, Double> entry : statistics.getHourHistogram().entrySet()) {
			tuples.add(new DataTuple<>(entry.getKey(), entry.getValue(), entry.getKey() + "h"));
		}
		dto.setHourHistogram(tuples);
		
		Integer totalCount = 0;
		for (Map.Entry<Integer, Integer> entry : statistics.getSuccessRateHistogram().entrySet()) {
			totalCount += entry.getValue();
		}
		
		List<DataTuple<Integer, Double>> successRateTuples = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : statistics.getSuccessRateHistogram().entrySet()) {
			successRateTuples.add(new DataTuple<>(entry.getKey(), 1d * entry.getValue() / totalCount, entry.getKey() * 5 + "%" ));
		}
		dto.setSuccessRateHistogram(successRateTuples);
		
		String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		List<DataTuple<Integer, Double>> weekdayRateTuples = new ArrayList<>();
		for (Map.Entry<Integer, Double> entry : statistics.getWeekdayHistogram().entrySet()) {
			weekdayRateTuples.add(new DataTuple<>(entry.getKey(), entry.getValue(), weekdays[entry.getKey()]));
		}
		dto.setWeekdayHistogram(weekdayRateTuples);
		
		return dto;
	}

}

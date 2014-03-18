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
			tuples.add(new DataTuple<>(entry.getKey(), entry.getValue()));
		}
		dto.setHourHistogram(tuples);
		return dto;
	}

}

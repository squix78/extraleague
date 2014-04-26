package ch.squix.extraleague.model.statistics;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
@Data
public class Statistics {
		
		@Id
		private Long id;
		
		@Serialize(zip=true)
		private Map<String, Integer> badgeHistogram = new HashMap<String, Integer>();

		@Serialize(zip=true)
		private Map<Integer, Double> hourHistogram;

		@Serialize(zip=true)
		private Map<Integer, Integer> successRateHistogram;

		@Serialize(zip=true)
		private Map<Integer, Double> weekdayHistogram;
		
		@Serialize(zip=true)
		private Map<Integer, Integer> eloHistogramm;
		private Integer minElo;
		private Integer maxElo;
		private Double eloBinRange;

}

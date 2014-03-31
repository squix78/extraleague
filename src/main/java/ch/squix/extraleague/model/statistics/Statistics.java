package ch.squix.extraleague.model.statistics;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
public class Statistics {
		
		@Id
		private Long id;
		
		@Serialize(zip=true)
		private Map<String, Integer> badgeHistogram = new HashMap<String, Integer>();

		@Serialize(zip=true)
		private Map<Integer, Double> histogram;

		@Serialize(zip=true)
		private Map<Integer, Integer> successRateHistogram;
		
		public Map<String, Integer> getBadgeHistogram() {
			return badgeHistogram;
		}

		public void setBadgeHistogram(Map<String, Integer> badgeHistogram) {
			this.badgeHistogram = badgeHistogram;
		}

		public void setHourHistogram(Map<Integer, Double> histogram) {
			this.histogram = histogram;
		}
		
		public Map<Integer, Double> getHourHistogram() {
			return histogram;
		}

		public void setSuccessRateHistogram(Map<Integer, Integer> successRateHistogram) {
			this.successRateHistogram = successRateHistogram;
		}
		
		public Map<Integer, Integer> getSuccessRateHistogram() {
			return successRateHistogram;
		}

}

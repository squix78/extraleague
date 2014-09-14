package ch.squix.extraleague.rest.ranking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.squix.extraleague.model.ranking.Ranking;

public class InTheDayRankingFilter {
	
	private final static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
	
	public static List<Ranking> getInTheDayRankings(List<Ranking> rankings) {
		Collections.sort(rankings, new CreatedDateCompartor());
		List<Ranking> inTheDayRankings = new ArrayList<>();
		String oldDateText = null;
		for (Ranking ranking : rankings) {
			String dateText = dayFormat.format(ranking.getCreatedDate());
			if (oldDateText != null && dateText.equals(oldDateText)) {
				inTheDayRankings.add(ranking);
			}
			oldDateText = dateText;
		}
		return inTheDayRankings;
	}
	
	private static class CreatedDateCompartor implements Comparator<Ranking> {

		@Override
		public int compare(Ranking o1, Ranking o2) {
			return o1.getCreatedDate().compareTo(o2.getCreatedDate());
		}
		
	}

}

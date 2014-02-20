package ch.squix.extraleague.model.match;

import java.util.Comparator;

public class MatchDateComparator implements Comparator<Match> {

	@Override
	public int compare(Match o1, Match o2) {
		if (o1.getEndDate() != null && o2.getEndDate() != null) {
			return o1.getEndDate().compareTo(o2.getEndDate());
		}
		if (o1.getEndDate() == o2.getEndDate()) {
			return 0;
		}
		if (o1.getEndDate() == null) {
			return -1;
		}
		return 1;
	}
	
}
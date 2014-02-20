package ch.squix.extraleague.model.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MatchDateComparatorTest {
	
	@Test
	public void shouldSortCorrectly() {
		List<Match> matches = new ArrayList<>();
		addTestMatch(matches, 0L, 10L);
		addTestMatch(matches, 1L, null);
		addTestMatch(matches, 2L, 8L);
		addTestMatch(matches, 3L, 12L);
		addTestMatch(matches, 4L, null);
		addTestMatch(matches, 5L, 2L);
		checkResult(matches, new Long[] {0L, 1L, 2L, 3L, 4L, 5L});
		Collections.sort(matches, new MatchDateComparator());
		checkResult(matches, new Long[] {1L, 4L, 5L, 2L, 0L, 3L});
	}

	private void checkResult(List<Match> matches, Long[] expectedIndexes) {
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(expectedIndexes[i], matches.get(i).getId());
		}
	}

	private void addTestMatch(List<Match> matches, Long id, Long timestamp) {
		Match match = new Match();
		if (timestamp != null) {
			match.setEndDate(new Date(timestamp));
		}
		match.setId(id);
		matches.add(match);
	}

}

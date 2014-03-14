package ch.squix.extraleague.rest.ranking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ch.squix.extraleague.model.ranking.Ranking;

public class InTheDayRankingFilterTest {
	
	private final static SimpleDateFormat DATE = new SimpleDateFormat("dd.MM.yyyy mm:hh");
	
	@Test
	public void shouldReturnAllRankingsButTheFirstOfEachDay() throws ParseException {
		List<Ranking> rankings = new ArrayList<>();
		rankings.add(createRanking(1L, "1.2.2014 11:30"));
		rankings.add(createRanking(2L, "1.2.2014 10:30"));
		rankings.add(createRanking(3L, "1.3.2014 10:30"));
		rankings.add(createRanking(4L, "1.3.2014 11:30"));
		rankings.add(createRanking(5L, "1.3.2014 11:31"));
		rankings.add(createRanking(6L, "1.4.2014 12:30"));
		List<Ranking> inTheDayRankings = InTheDayRankingFilter.getInTheDayRankings(rankings);
		Assert.assertEquals(3, inTheDayRankings.size());
		Assert.assertEquals(Long.valueOf(1L), inTheDayRankings.get(0).getId());
		Assert.assertEquals(Long.valueOf(4L), inTheDayRankings.get(1).getId());
		Assert.assertEquals(Long.valueOf(5L), inTheDayRankings.get(2).getId());
	}

	private Ranking createRanking(Long id, String dateText) throws ParseException {
		Ranking ranking = new Ranking();
		ranking.setId(id);
		ranking.setCreatedDate(DATE.parse(dateText));
		return ranking;
	}

}

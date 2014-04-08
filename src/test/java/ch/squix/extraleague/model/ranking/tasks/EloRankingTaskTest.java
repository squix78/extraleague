package ch.squix.extraleague.model.ranking.tasks;

import junit.framework.Assert;

import org.junit.Test;

import ch.squix.extraleague.model.ranking.elo.EloUtil;

public class EloRankingTaskTest {
	
	@Test
	public void shouldCaculateCorrectProbability() {
		
		Double expectation = EloUtil.getExpectedOutcome(2806, 2577);
		
		Assert.assertEquals(0.7888870682101548, expectation);
	}

}

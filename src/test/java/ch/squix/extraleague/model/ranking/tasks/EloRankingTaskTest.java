package ch.squix.extraleague.model.ranking.tasks;

import junit.framework.Assert;

import org.junit.Test;

public class EloRankingTaskTest {
	
	@Test
	public void shouldCaculateCorrectProbability() {
		EloRankingTask task = new EloRankingTask();
		
		Double expectation = task.getExpectedOutcome(2806, 2577);
		
		Assert.assertEquals(0.7888870682101548, expectation);
	}

}

package ch.squix.extraleague.server;

import junit.framework.Assert;

import org.junit.Test;

public class NamespaceFilterTest {
	
	@Test
	public void shouldReturnCorrectSubdomain() {
		NamespaceFilter filter = new NamespaceFilter();
		
		Assert.assertEquals("ncaleague", filter.getSubdomain("ncaleague.appspot.com"));
		Assert.assertEquals("ncaleague", filter.getSubdomain("ncaleague.toeggele.ch"));
		Assert.assertEquals("ncaleague", filter.getSubdomain("ncaleague.toeggele.org"));
		Assert.assertEquals("ncaleague", filter.getSubdomain("1.ncaleague.toeggele.org"));
		Assert.assertEquals("nca-league", filter.getSubdomain("nca-league.toeggele.org"));
	}
	@Test
	public void shouldReturnNull() {
		NamespaceFilter filter = new NamespaceFilter();
		
		Assert.assertNull(filter.getSubdomain("localhost:5222"));

	}

}

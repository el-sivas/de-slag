package de.slag.central.data.impl.test;

import org.junit.Assert;
import org.junit.Test;

public class PersistBeanTest {

	@Test
	public void testElseAttributes() {
		final TestPersistBean tpb = new TestPersistBean();
		Assert.assertTrue(tpb.getCreatedAt() != null);
		Assert.assertNull(tpb.getTestString());
		final String test = "x";
		tpb.setTestString(test);
		Assert.assertNotNull(tpb.getTestString());
		Assert.assertTrue(test.equals(tpb.getTestString()));
	}

}

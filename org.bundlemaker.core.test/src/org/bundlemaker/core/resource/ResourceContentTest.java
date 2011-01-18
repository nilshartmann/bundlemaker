package org.bundlemaker.core.resource;

import org.bundlemaker.core.projectdescription.ResourceContent;
import org.bundlemaker.core.util.StopWatch;
import org.junit.Assert;
import org.junit.Test;

public class ResourceContentTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testResourceContent() {

		ResourceContent content = new ResourceContent();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (int i = 0; i < 100000; i++) {

			content.getModifiableBinaryResources().add(
					new ResourceStandin("id", "root", "path" + i));
		}

		stopWatch.stop();

		Assert.assertEquals(100000, content.getModifiableBinaryResources()
				.size());

		Assert.assertTrue(stopWatch.getElapsedTime() < 150);
	}
}

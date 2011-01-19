package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.bundlemaker.core.util.StopWatch;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testCreateOrGetReference() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		FlyWeightCache referenceCache = new FlyWeightCache();

		for (int i = 0; i < 50000; i++) {

			Resource resource = new Resource("contentId", "root", "path",
					referenceCache);

			for (int j = 0; j < 30; j++) {
				resource.createReference("name" + j,
						ReferenceType.PACKAGE_REFERENCE, true, true, true, true);
			}

			Assert.assertEquals(30, resource.getReferences().size());
		}

		stopWatch.stop();

		Assert.assertTrue(
				String.format("Elapsed time '%s'.", stopWatch.getElapsedTime()),
				stopWatch.getElapsedTime() < 3000);
		Assert.assertEquals(30, referenceCache._referenceCache.size());

	}
}

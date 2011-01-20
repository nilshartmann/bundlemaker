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

		int resourcesCount = 50000;
		int referencesCount = 30;
		int cacheSize = resourcesCount * referencesCount;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		FlyWeightCache referenceCache = new FlyWeightCache();

		for (int i = 0; i < resourcesCount; i++) {

			Resource resource = new Resource("contentId", "root", "path",
					referenceCache);

			for (int j = 0; j < referencesCount; j++) {
				resource.createReference("name" + i + "#" + j,
						ReferenceType.PACKAGE_REFERENCE, true, true);
			}

			Assert.assertEquals(referencesCount, resource.getReferences()
					.size());
		}

		stopWatch.stop();

		// Assert.assertTrue(
		// String.format("Elapsed time '%s'.", stopWatch.getElapsedTime()),
		// stopWatch.getElapsedTime() < 3000);
		Assert.assertEquals(cacheSize, referenceCache._referenceCache.size());
		stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 10000; i++) {

			Reference reference = referenceCache.getReference(
					"name" + i + "#1", ReferenceType.PACKAGE_REFERENCE, true,
					true);

			Assert.assertNotNull(reference);
		}
		stopWatch.stop();
		System.out.println("Existing ones " + stopWatch.getElapsedTime());

		Assert.assertEquals(cacheSize, referenceCache._referenceCache.size());
		stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 10000; i++) {

			Reference reference = referenceCache.getReference("name" + i
					+ "#40", ReferenceType.PACKAGE_REFERENCE, true, true);

			Assert.assertNotNull(reference);
		}
		stopWatch.stop();
		System.out.println("Non existing ones " + stopWatch.getElapsedTime());
	}
}

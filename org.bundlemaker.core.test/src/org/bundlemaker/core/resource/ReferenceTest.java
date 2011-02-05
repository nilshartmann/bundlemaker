package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.resource.Reference;
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

		//
		ResourceCache resourceCache = new ResourceCache(
				new DummyDependencyStore());

		for (int i = 0; i < resourcesCount; i++) {

			Resource resource = new Resource("contentId", "root", "path",
					resourceCache);

			for (int j = 0; j < referencesCount; j++) {
				resource.recordReference("name" + i + "#" + j,
						ReferenceType.PACKAGE_REFERENCE, true, true, true, false,
						false);
			}

			Assert.assertEquals(referencesCount, resource.getReferences()
					.size());
		}

		stopWatch.stop();

		// Assert.assertTrue(
		// String.format("Elapsed time '%s'.", stopWatch.getElapsedTime()),
		// stopWatch.getElapsedTime() < 3000);
		Assert.assertEquals(cacheSize, resourceCache.getFlyWeightCache().getReferenceCache().size());
		stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 10000; i++) {

			Reference reference = resourceCache.getFlyWeightCache().getReference(
					"name" + i + "#1", ReferenceType.PACKAGE_REFERENCE, true, true,
					true, false, false);

			Assert.assertNotNull(reference);
		}
		stopWatch.stop();
		System.out.println("Existing ones " + stopWatch.getElapsedTime());

		Assert.assertEquals(cacheSize, resourceCache.getFlyWeightCache().getReferenceCache().size());
		stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 10000; i++) {

			Reference reference = resourceCache.getFlyWeightCache().getReference("name" + i
					+ "#40", ReferenceType.PACKAGE_REFERENCE, true, true, true, false, false);

			Assert.assertNotNull(reference);
		}
		stopWatch.stop();
		System.out.println("Non existing ones " + stopWatch.getElapsedTime());
	}
}

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
public class ResourceTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testResource() {

		Resource resource = new Resource("contentId", "root", "path");

		Assert.assertEquals("contentId", resource.getContentId());
		Assert.assertEquals("root", resource.getRoot());
		Assert.assertEquals("path", resource.getPath());

		Assert.assertTrue(resource.getAssociatedResources().isEmpty());
		Assert.assertTrue(resource.getReferences().isEmpty());
		Assert.assertTrue(resource.getContainedTypes().isEmpty());
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableAssociatedResources() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getAssociatedResources().add(null);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableReferences() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getReferences().add(null);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableContainedTypes() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getContainedTypes().add(null);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testResourcePerformance() {

		int resourceCount = 50000;
		int referenceCount = 50;

		//
		FlyWeightCache flyWeightCache = new FlyWeightCache();

		//
		StopWatch stopWatch_1 = new StopWatch();
		stopWatch_1.start();

		//
		Resource resource = new Resource("start", "start", "start",
				flyWeightCache);
		for (int j = 0; j < 100; j++) {
			resource.createReference("start" + j, ReferenceType.TYPE_REFERENCE,
					true, true);
		}

		stopWatch_1.stop();

		//
		for (int i = 0; i < resourceCount; i++) {

			//
			resource = new Resource("00", "aa" + i, "bb", flyWeightCache);

			//
			for (int j = 0; j < referenceCount; j++) {
				resource.createReference("" + i + j,
						ReferenceType.TYPE_REFERENCE, true, true);
			}
		}

		//
		StopWatch stopWatch_2 = new StopWatch();
		stopWatch_2.start();

		//
		resource = new Resource("stop", "stop", "stop", flyWeightCache);
		for (int j = 0; j < 100; j++) {
			resource.createReference("stop" + j, ReferenceType.TYPE_REFERENCE,
					true, true);
		}

		stopWatch_2.stop();

		Assert.assertEquals(2350242, flyWeightCache._flyWeightStrings.size());
		Assert.assertEquals(1, flyWeightCache._referenceAttributesCache.size());
		Assert.assertEquals(2300240, flyWeightCache._referenceCache.size());

		// assert linear access time
		Assert.assertTrue(
				stopWatch_1.getElapsedTime() + " : "
						+ stopWatch_2.getElapsedTime(),
				Math.abs(stopWatch_1.getElapsedTime()
						- stopWatch_2.getElapsedTime()) < 20);
	}
}

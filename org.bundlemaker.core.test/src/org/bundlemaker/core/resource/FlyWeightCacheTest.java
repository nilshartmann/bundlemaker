package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.junit.Test;

public class FlyWeightCacheTest {

	public static final int TIMEOUT = 500;

	/**
	 * <p>
	 * OK
	 * </p>
	 */
	@Test(timeout = TIMEOUT)
	public void testGetFlyWeightString() {

		//
		FlyWeightCache cache = new FlyWeightCache();

		//
		for (int i = 0; i < 100000; i++) {

			//
			FlyWeightString flyWeightString_1 = cache.getFlyWeightString("abc"
					+ i);
			//
			FlyWeightString flyWeightString_2 = cache.getFlyWeightString("abc");
		}

		//
		Assert.assertEquals(100001, cache._flyWeightStrings.size());
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(timeout = TIMEOUT)
	public void testGetReferenceAttributes() {

		//
		FlyWeightCache cache = new FlyWeightCache();

		//
		for (int i = 0; i < 100000; i++) {

			//
			ReferenceAttributes attributes = cache.getReferenceAttributes(
					i % 11 == 0 ? ReferenceType.TYPE_REFERENCE
							: ReferenceType.PACKAGE_REFERENCE, i % 2 == 0,
					i % 3 == 0, i % 5 == 0, i % 7 == 0);
		}

		//
		Assert.assertEquals(32, cache._referenceAttributesCache.size());
	}

	@Test
	public void testGetReference() {

		//
		FlyWeightCache cache = new FlyWeightCache();

		//
		for (int i = 0; i < 1000000; i++) {

			//
			Reference reference = cache.getReference("fullyQualifiedName" + i,
					i % 11 == 0 ? ReferenceType.TYPE_REFERENCE
							: ReferenceType.PACKAGE_REFERENCE, i % 2 == 0,
					i % 3 == 0, i % 5 == 0, i % 7 == 0);

			reference = cache.getReference("fullyQualifiedName",
					i % 11 == 0 ? ReferenceType.TYPE_REFERENCE
							: ReferenceType.PACKAGE_REFERENCE, i % 2 == 0,
					i % 3 == 0, i % 5 == 0, i % 7 == 0);
		}

		//
		Assert.assertEquals(1000032, cache._referenceCache.size());
	}
}

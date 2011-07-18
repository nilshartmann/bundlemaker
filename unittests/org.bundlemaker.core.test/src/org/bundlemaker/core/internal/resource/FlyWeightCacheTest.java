package org.bundlemaker.core.internal.resource;

import junit.framework.Assert;

import org.bundlemaker.core.internal.resource.FlyWeightCache;
import org.bundlemaker.core.internal.resource.Reference;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.junit.Test;

public class FlyWeightCacheTest {

  public static final int TIMEOUT = 750;

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
      FlyWeightString flyWeightString_1 = cache.getFlyWeightString("abc" + i);
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
  @Test
  public void testGetReferenceAttributes() {

    //
    FlyWeightCache cache = new FlyWeightCache();

    //
    for (int i = 1; i <= 1000; i++) {
      cache.getReference("fullyQualifiedName" + i, new ReferenceAttributes(mask(i, 1) ? ReferenceType.TYPE_REFERENCE
          : ReferenceType.PACKAGE_REFERENCE, mask(i, 2), mask(i, 4), mask(i, 8), mask(i, 16), mask(i, 32), mask(i, 64),
          mask(i, 128)));
    }

    //
    Assert.assertEquals(256, cache._referenceAttributesCache.size());
  }

  @Test(timeout = 9000)
  public void testGetReference() {

    //
    FlyWeightCache cache = new FlyWeightCache();

    //
    for (int i = 0; i < 1000; i++) {

      //
      cache.getReference("fullyQualifiedName" + i, new ReferenceAttributes(mask(i, 1) ? ReferenceType.TYPE_REFERENCE
          : ReferenceType.PACKAGE_REFERENCE, mask(i, 2), mask(i, 4), mask(i, 8), mask(i, 16), mask(i, 32), mask(i, 64),
          mask(i, 128)));

      cache.getReference("fullyQualifiedName" + i, new ReferenceAttributes(mask(i, 1) ? ReferenceType.TYPE_REFERENCE
          : ReferenceType.PACKAGE_REFERENCE, mask(i, 2), mask(i, 4), mask(i, 8), mask(i, 16), mask(i, 32), mask(i, 64),
          mask(i, 128)));
    }

    //
    Assert.assertEquals(1000, cache._referenceCache.size());
  }

  /**
   * @param value
   * @param mask
   * @return
   */
  private static boolean mask(int value, int mask) {
    return (value & mask) == mask;
  }
}

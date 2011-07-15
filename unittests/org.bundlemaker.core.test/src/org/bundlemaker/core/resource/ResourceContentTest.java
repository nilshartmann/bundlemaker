package org.bundlemaker.core.resource;

import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.junit.Assert;
import org.junit.Test;

public class ResourceContentTest {

  /**
   * <p>
   * </p>
   */
  @Test(timeout = 500)
  public void testResourceContent() {

    ResourceContent content = new ResourceContent();

    for (int i = 0; i < 100000; i++) {

      content.getModifiableBinaryResources().add(new ResourceStandin("id", "root", "path" + i));
    }

    Assert.assertEquals(100000, content.getModifiableBinaryResources().size());
  }
}

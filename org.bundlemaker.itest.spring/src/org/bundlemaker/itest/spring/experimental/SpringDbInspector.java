package org.bundlemaker.itest.spring.experimental;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.test.inspector.DbInspector;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class SpringDbInspector {

  @Test
  public void test() throws CoreException {

    DbInspector dbInspector = new DbInspector();
    dbInspector.testDatabase(BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature("spring"));

  }
}

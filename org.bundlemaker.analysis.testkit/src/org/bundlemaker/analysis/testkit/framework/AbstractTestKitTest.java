package org.bundlemaker.analysis.testkit.framework;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.junit.BeforeClass;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractTestKitTest {

  /** - */
  private static final String    TEST_KIT_ADAPTER_CLASS = "org.bundlemaker.analysis.testkit.TestKitAdapter";

  /** - */
  private static ITestKitAdapter _testKitAdapter;

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void init() throws Exception {
    if (_testKitAdapter == null) {
      _testKitAdapter = createTestKitAdapter();
      _testKitAdapter.init();
      
      // write dependency model to disc
      Util.dumpToFile(_testKitAdapter.getRoot());
    }
  }

  public IDependencyModel getDependencyModel() {
    return _testKitAdapter.getDependencyModel();
  }

  public IArtifact getRoot() {
    return _testKitAdapter.getRoot();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected static ITestKitAdapter createTestKitAdapter() {

    try {

      // the clazz object
      Class<?> clazz = AbstractTestKitTest.class.getClassLoader().loadClass(TEST_KIT_ADAPTER_CLASS);

      // create the new instance
      return (ITestKitAdapter) clazz.newInstance();

    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
      return null;
    }
  }
}

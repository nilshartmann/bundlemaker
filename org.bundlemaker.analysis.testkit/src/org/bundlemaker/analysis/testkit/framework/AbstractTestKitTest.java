package org.bundlemaker.analysis.testkit.framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.testutils.ArtifactTestUtil;
import org.bundlemaker.core.testutils.BundleMakerTestUtils;
import org.bundlemaker.core.testutils.FileDiffUtil;
import org.junit.BeforeClass;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractTestKitTest {

  /** - */
  private static final String    TEST_KIT_ADAPTER_CLASS     = "org.bundlemaker.analysis.testkit.TestKitAdapter";

  /** - */
  private static ITestKitAdapter _testKitAdapter;

  /** - */
  private static Throwable       _modelCheckFailedException = null;

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void init() throws Throwable {

    if (_modelCheckFailedException != null) {
      throw _modelCheckFailedException;
    }

    try {

      //
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

      String timestamp = dateFormat.format(new Date());

      if (_testKitAdapter == null) {

        //
        _testKitAdapter = createTestKitAdapter();

        //
        if (_testKitAdapter instanceof ITimeStampAwareTestKitAdapter) {
          ((ITimeStampAwareTestKitAdapter) _testKitAdapter).setTimeStamp(timestamp);
        }

        //
        _testKitAdapter.init();

        // actual
        File actual = new File(System.getProperty("user.dir"), "result" + File.separatorChar + "ArtifactModel_"
            + timestamp + ".txt");
        BundleMakerTestUtils.writeToFile(ArtifactTestUtil.toString(_testKitAdapter.getRoot()), actual);

        // expected
        File expected = new File(System.getProperty("user.dir"), "test-data/ArtifactModel_Expected.txt");

        // htmlReport
        String name = actual.getAbsolutePath();
        name = name.substring(0, name.length() - ".txt".length()) + ".html";
        File htmlReport = new File(name);

        // assert
        FileDiffUtil.assertArtifactModel(expected, actual, htmlReport);
      }

    } catch (Throwable e) {
      _modelCheckFailedException = e;
      throw e;
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

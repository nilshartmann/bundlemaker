package org.bundlemaker.analysis.testkit.framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.IArtifact;
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

  /** - */
  private static String          _timestamp;

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void init() throws Throwable {

    // re-throw previous exception
    if (_modelCheckFailedException != null) {
      throw _modelCheckFailedException;
    }

    //
    _timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());

    //
    try {

      //
      if (_testKitAdapter == null) {

        //
        _testKitAdapter = createTestKitAdapter();

        //
        if (_testKitAdapter instanceof ITimeStampAwareTestKitAdapter) {
          ((ITimeStampAwareTestKitAdapter) _testKitAdapter).setTimeStamp(_timestamp);
        }

        //
        _testKitAdapter.init();

        assertTestArtifactModel();
      }

    } catch (Throwable e) {
      _modelCheckFailedException = e;
      throw e;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static String getTimestamp() {
    return _timestamp;
  }

//  /**
//   * <p>
//   * </p>
//   * 
//   * @return
//   */
//  public IDependencyModel getDependencyModel() {
//    return _testKitAdapter.getDependencyModel();
//  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
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

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  private static void assertTestArtifactModel() throws Exception {

    // actual
    File actual = new File(System.getProperty("user.dir"), "result" + File.separatorChar + "ArtifactModel_"
        + _timestamp + ".txt");

    IArtifact group1 = _testKitAdapter.getRoot().getChild("group1");
    IArtifact velocity_15 = _testKitAdapter.getRoot().getChild("velocity_1.5");

    StringBuilder builder = new StringBuilder();
    builder.append(ArtifactTestUtil.toString(group1));
    builder.append("\n");
    builder.append(ArtifactTestUtil.toString(velocity_15));

    BundleMakerTestUtils.writeToFile(builder.toString(), actual);

    // expected
    File expected = new File(System.getProperty("user.dir"), "test-data/ArtifactModel_Expected.txt");

    // htmlReport
    String name = actual.getAbsolutePath();
    name = name.substring(0, name.length() - ".txt".length()) + ".html";
    File htmlReport = new File(name);

    // assert
    FileDiffUtil.assertArtifactModel(expected, actual, htmlReport);
  }
}

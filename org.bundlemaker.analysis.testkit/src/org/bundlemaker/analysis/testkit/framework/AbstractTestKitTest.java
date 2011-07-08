package org.bundlemaker.analysis.testkit.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import junit.framework.Assert;
import name.fraser.neil.diff_match_patch;
import name.fraser.neil.diff_match_patch.Diff;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
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
  private static final String    TEST_KIT_ADAPTER_CLASS = "org.bundlemaker.analysis.testkit.TestKitAdapter";

  /** - */
  private static ITestKitAdapter _testKitAdapter;

  /** - */
  private static boolean         _modelCheckFailed      = false;

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void init() throws Exception {

    if (_modelCheckFailed) {
      Assert.fail("");
    }

    
    //
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    
    String timestamp = dateFormat.format(new Date());
    
    if (_testKitAdapter == null) {
      
      //
      _testKitAdapter = createTestKitAdapter();
      
      //
      if (_testKitAdapter instanceof ITimeStampAwareTestKitAdapter) {
        ((ITimeStampAwareTestKitAdapter)_testKitAdapter).setTimeStamp(timestamp);
      }
      
      //
      _testKitAdapter.init();

      // actual
      File actual = new File(System.getProperty("user.dir"), "result" + File.separatorChar + "DependencyModel_"
          + timestamp + ".txt");
      BundleMakerTestUtils.writeToFile(ArtifactTestUtil.toString(_testKitAdapter.getRoot()), actual);

      //expected
      File expected = new File(System.getProperty("user.dir"), "test-data/JEdit_ExpectedResult.txt");

      // htmlReport
      String name = actual.getAbsolutePath();
      name = name.substring(0, name.length() - ".txt".length()) + ".html";
      File htmlReport = new File(name);

      // assert
      FileDiffUtil.assertArtifactModel(expected, actual, htmlReport);
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

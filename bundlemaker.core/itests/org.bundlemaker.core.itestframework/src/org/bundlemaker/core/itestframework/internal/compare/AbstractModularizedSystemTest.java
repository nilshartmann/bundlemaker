package org.bundlemaker.core.itestframework.internal.compare;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.itestframework.utils.BundleMakerTestUtils;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractModularizedSystemTest {

  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  /**
   * <p>
   * </p>
   */
  protected void log(String message) {
    System.out.println(String.format("[%s] %s", this.getClass().getName(), message));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  @Before
  public void before() throws CoreException {


  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param type
   * @param nodeName
   * @param parentName
   */
  protected void assertNode(IBundleMakerArtifact node, Class<?> type, String nodeName, String parentName) {
    Assert.assertTrue(String.format("Node '%s' has to be assignable from %s", node, type),
        type.isAssignableFrom(node.getClass()));
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
    Assert.assertEquals(parentName, node.getParent().getName());
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param type
   * @param nodeName
   */
  protected void assertNode(IBundleMakerArtifact node, Class<?> type, String nodeName) {
    Assert.assertTrue(String.format("Node '%s' has to be assignable from %s", node, type),
        type.isAssignableFrom(node.getClass()));
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param resourceModule
   * @param expectedResult
   * @throws Exception
   */
  protected static void assertResult(String dumpedModel, InputStream expected, String resultFileName) {

    Assert.assertNotNull(dumpedModel);
    Assert.assertNotNull(expected);
    Assert.assertNotNull(resultFileName);

    // actual
    File actual = new File(System.getProperty("user.dir"), "result" + File.separatorChar + resultFileName + ".txt");
    StringBuilder builder = new StringBuilder();
    builder.append(dumpedModel);
    BundleMakerTestUtils.writeToFile(builder.toString(), actual);

    // htmlReport
    String name = actual.getAbsolutePath();
    name = name.substring(0, name.length() - ".txt".length()) + ".html";
    File htmlReport = new File(name);

    // assert
    FileDiffUtil.assertArtifactModel(expected, new ByteArrayInputStream(dumpedModel.getBytes()), htmlReport);

    //
    actual.delete();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected static String getCurrentTimeStamp() {
    return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
  }
}